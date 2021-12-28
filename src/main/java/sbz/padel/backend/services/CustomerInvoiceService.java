package sbz.padel.backend.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import sbz.padel.backend.generics.BaseService;
import sbz.padel.backend.repositories.CustomerInvoiceRepository;
import sbz.padel.backend.repositories.CustomerRepository;
import sbz.padel.backend.entities.Customer;
import sbz.padel.backend.entities.CustomerInvoice;

@Service
public class CustomerInvoiceService extends BaseService<CustomerInvoice> {
    private final CustomerInvoiceRepository customerInvoiceRepository;
    private final CustomerRepository customerRepository;

    public CustomerInvoiceService(CustomerInvoiceRepository customerInvoiceRepository,
            CustomerRepository customerRepository) {
        super(customerInvoiceRepository);
        this.customerInvoiceRepository = customerInvoiceRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerInvoice save(CustomerInvoice CustomerInvoice) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(CustomerInvoice.getCustomer().getId());
            Customer customer = opCustomer.get();
            customer.setSolde(customer.getSolde() + CustomerInvoice.getSolde());
            customer.setSoldeType(customer.getSolde() >= 0);
            this.customerRepository.save(customer);
            return this.customerInvoiceRepository.save(CustomerInvoice);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed");
        }

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<CustomerInvoice> opCustomerInvoice = this.customerInvoiceRepository.findById(id);
            CustomerInvoice oldInvoice = opCustomerInvoice.get();
            Optional<Customer> opCustomer = this.customerRepository.findById(oldInvoice.getCustomer().getId());
            Customer customer = opCustomer.get();
            customer.setSolde(customer.getSolde() - oldInvoice.getSolde());
            customer.setSoldeType(customer.getSolde() >= 0);
            this.customerInvoiceRepository.deleteById(id);
            this.customerRepository.save(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed");
        }
    }

    @Transactional
    public CustomerInvoice update(CustomerInvoice customerInvoice, Long id) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(customerInvoice.getCustomer().getId());
            Customer customer = opCustomer.get();
            Optional<CustomerInvoice> opCustomerInvoice = this.customerInvoiceRepository.findById(id);
            CustomerInvoice oldInvoice = opCustomerInvoice.get();
            Customer oldCustomer = this.customerRepository.findById(oldInvoice.getCustomer().getId()).get();

            if (customer.getId() == oldInvoice.getCustomer().getId()) {
                customer.setSolde(customer.getSolde() + customerInvoice.getSolde() - oldInvoice.getSolde());
            } else {
                oldCustomer.setSolde(oldCustomer.getSolde() - oldInvoice.getSolde());
                customer.setSolde(customer.getSolde() + customerInvoice.getSolde());
                oldCustomer.setSoldeType(customer.getSolde() >= 0);
                this.customerRepository.save(oldCustomer);

            }

            oldInvoice.setCustomer(customer);
            oldInvoice.setDescription(customerInvoice.getDescription());
            oldInvoice.setSolde(customerInvoice.getSolde());
            oldInvoice.setId(id);
            customer.setSoldeType(customer.getSolde() >= 0);
            this.customerRepository.save(customer);
            return this.customerInvoiceRepository.save(oldInvoice);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed");
        }
    }
}
