package sbz.padel.backend.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import sbz.padel.backend.config.RestServerResponseException;
import sbz.padel.backend.entities.Customer;
import sbz.padel.backend.entities.CustomerInvoice;
import sbz.padel.backend.entities.CustomerTransaction;
import sbz.padel.backend.generics.BaseService;
import sbz.padel.backend.repositories.CustomerInvoiceRepository;
import sbz.padel.backend.repositories.CustomerRepository;
import sbz.padel.backend.repositories.CustomerTransactionRepository;

@Service
public class CustomerService extends BaseService<Customer> {

    private final CustomerRepository customerRepository;
    private final CustomerTransactionRepository customerTransactionRepository;
    private final CustomerInvoiceRepository customerInvoiceRepository;

    public CustomerService(CustomerRepository customerRepository,
            CustomerTransactionRepository customerTransactionRepository,
            CustomerInvoiceRepository customerInvoiceRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
        this.customerTransactionRepository = customerTransactionRepository;
        this.customerInvoiceRepository = customerInvoiceRepository;
    }

    @Override
    public Customer save(Customer customer) {
        customer.setSolde(customer.getSoldeInitial());
        customer.setSoldeType(customer.getSolde() >= 0);
        return this.customerRepository.save(customer);
    }

    public Page<Customer> findAllByActiveAndCin(boolean isActive, String cin,
            Pageable pageable) {
        return this.customerRepository.findAllFilterByActiveAndCin(isActive, cin, pageable);
    }

    @Transactional
    public Customer update(Customer customer, Long id) {
            Customer existingCustomer = this.customerRepository.findById(id).orElseThrow(()-> new RestServerResponseException(HttpStatus.NOT_FOUND,"Client introuvable!"));
            if (customer.getAddress() != null)
                existingCustomer.setAddress(customer.getAddress());
            if (customer.getBank() != null)
                existingCustomer.setBank(customer.getBank());
            if (customer.getBankAccount() != null)
                existingCustomer.setBankAccount(customer.getBankAccount());
            if (customer.getCin() != null)
                existingCustomer.setCin(customer.getCin());
            if (customer.getGuarantee() != null)
                existingCustomer.setGuarantee(customer.getGuarantee());
            if (customer.getName() != null)
                existingCustomer.setName(customer.getName());
            if (customer.getTelephone() != null)
                existingCustomer.setTelephone(customer.getTelephone());
            return this.customerRepository.save(existingCustomer);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(id);
            Customer customer = opCustomer.get();
            List<CustomerTransaction> customerTransactions = customer.getTransactions();
            for (CustomerTransaction customerTransaction : customerTransactions) {
                customerTransaction.setActive(false);
                this.customerTransactionRepository.save(customerTransaction);
            }
            List<CustomerInvoice> customerInvoices = customer.getInvoices();
            for (CustomerInvoice customerInvoice : customerInvoices) {
                customerInvoice.setActive(false);
                this.customerInvoiceRepository.save(customerInvoice);
            }
            this.customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed");
        }
    }

    @Transactional
    public Customer restoreById(Long id) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(id);
            Customer existingCustomer = opCustomer.get();
            if (!existingCustomer.getActive()) {
                List<CustomerInvoice> customerInvoices = existingCustomer.getInvoices();
                for (CustomerInvoice customerInvoice : customerInvoices) {
                    customerInvoice.setActive(true);
                    this.customerInvoiceRepository.save(customerInvoice);
                }
                List<CustomerTransaction> customerTransactions = existingCustomer.getTransactions();
                for (CustomerTransaction customerTransaction : customerTransactions) {
                    customerTransaction.setActive(true);
                    this.customerTransactionRepository.save(customerTransaction);
                }
                existingCustomer.setActive(true);
            }

            return this.customerRepository.save(existingCustomer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed");
        }
    }

}
