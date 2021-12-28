package sbz.padel.backend.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import sbz.padel.backend.entities.Customer;
import sbz.padel.backend.entities.CustomerTransaction;
import sbz.padel.backend.generics.BaseService;
import sbz.padel.backend.repositories.CustomerRepository;
import sbz.padel.backend.repositories.CustomerTransactionRepository;

@Service
public class CustomerTransactionService extends BaseService<CustomerTransaction> {
    private final CustomerTransactionRepository customerTransactionRepository;
    private final CustomerRepository customerRepository;

    public CustomerTransactionService(CustomerTransactionRepository customerTransactionRepository,
            CustomerRepository customerRepository) {
        super(customerTransactionRepository);
        this.customerTransactionRepository = customerTransactionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerTransaction save(CustomerTransaction customerTransaction) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(customerTransaction.getCustomer().getId());
            Customer customer = opCustomer.get();
            if (customerTransaction.isSoldeType()) {
                customer.setSolde(customer.getSolde() + customerTransaction.getSolde());
            } else {
                customer.setSolde(customer.getSolde() - customerTransaction.getSolde());
            }
            customer.setSoldeType(customer.getSolde() >= 0);
            customerTransaction.setCustomer(this.customerRepository.save(customer));
            return this.customerTransactionRepository.save(customerTransaction);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed");
        }

    }

    public Page<CustomerTransaction> findAllBetween(boolean isActive, LocalDate fromDate, LocalDate toDate,
            Pageable pageable) {
        return this.customerTransactionRepository.findAllFilterByActiveAndCreatedAtBetween(isActive, fromDate, toDate,
                pageable);
    }

    public Page<CustomerTransaction> findAllByCustomerDateBetween(boolean isActive, LocalDate fromDate,
            LocalDate toDate,
            Long customerId,
            Pageable pageable) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(customerId);
            Customer existingCustomer = opCustomer.get();
            return this.customerTransactionRepository.findAllFilterByActiveAndCustomerAndCreatedAtBetween(isActive,
                    existingCustomer, fromDate, toDate,
                    pageable);
        } catch (Exception e) {
            throw new EntityExistsException();
        }

    }

    public Page<CustomerTransaction> findAllByCustomerCredit(boolean isActive,
            Long customerId,
            Pageable pageable) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(customerId);
            Customer existingCustomer = opCustomer.get();
            return this.customerTransactionRepository.findAllFilterByActiveAndCustomerAndSoldeType(isActive,
                    existingCustomer,
                    false,
                    pageable);
        } catch (Exception e) {
            throw new EntityExistsException();
        }

    }

    public Page<CustomerTransaction> findAllByCustomer(boolean isActive,
            Long customerId,
            Pageable pageable) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(customerId);
            Customer existingCustomer = opCustomer.get();
            return this.customerTransactionRepository.findAllFilterByActiveAndCustomer(isActive,
                    existingCustomer,
                    pageable);
        } catch (Exception e) {
            throw new EntityExistsException();
        }

    }

    public Page<CustomerTransaction> findAllByCustomerDebit(boolean isActive,
            Long customerId,
            Pageable pageable) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(customerId);
            Customer existingCustomer = opCustomer.get();
            return this.customerTransactionRepository.findAllFilterByActiveAndCustomerAndSoldeType(isActive,
                    existingCustomer,
                    true,
                    pageable);
        } catch (Exception e) {
            throw new EntityExistsException();
        }

    }

    public Page<CustomerTransaction> findAllByCustomerCreatedAtDesc(boolean isActive,
            Long customerId,
            Pageable pageable) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(customerId);
            Customer existingCustomer = opCustomer.get();
            return this.customerTransactionRepository.findAllFilterByActiveAndCustomerOrderByCreatedAtDesc(isActive,
                    existingCustomer,
                    pageable);
        } catch (Exception e) {
            throw new EntityExistsException();
        }

    }

    public Page<CustomerTransaction> findAllDesc(boolean isActive, Pageable pageable) {
        return this.customerTransactionRepository.findAllFilterByActiveOrderByCreatedAtDesc(isActive, pageable);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, this.getClass().getName() + "id should not be null");
        try {
            Optional<CustomerTransaction> opCustomerTransaction = this.customerTransactionRepository.findById(id);
            CustomerTransaction olTransaction = opCustomerTransaction.get();
            Optional<Customer> opCustomer = this.customerRepository.findById(olTransaction.getCustomer().getId());
            Customer customer = opCustomer.get();
            if (olTransaction.isSoldeType()) {
                customer.setSolde(customer.getSolde() - olTransaction.getSolde());
            } else {
                customer.setSolde(customer.getSolde() + olTransaction.getSolde());
            }
            customer.setSoldeType(customer.getSolde() >= 0);

            this.customerTransactionRepository.deleteById(id);
            this.customerRepository.save(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed");
        }
    }

    @Transactional
    public CustomerTransaction update(CustomerTransaction customerTransaction, Long id) {
        try {
            Optional<Customer> opCustomer = this.customerRepository.findById(customerTransaction.getCustomer().getId());
            Customer customer = opCustomer.get();
            Optional<CustomerTransaction> opCustomerTransaction = this.customerTransactionRepository.findById(id);
            CustomerTransaction olTransaction = opCustomerTransaction.get();
            Customer oldCustomer = this.customerRepository.findById(olTransaction.getCustomer().getId()).get();

            if (customer.getId() == olTransaction.getCustomer().getId()) {
                if (customerTransaction.isSoldeType()) {
                    customer.setSolde(customer.getSolde() + customerTransaction.getSolde());
                } else {
                    customer.setSolde(customer.getSolde() - customerTransaction.getSolde());
                }
                if (olTransaction.isSoldeType()) {
                    customer.setSolde(customer.getSolde() - olTransaction.getSolde());
                } else {
                    customer.setSolde(customer.getSolde() + olTransaction.getSolde());
                }
            } else {
                if (olTransaction.isSoldeType()) {
                    oldCustomer.setSolde(oldCustomer.getSolde() - olTransaction.getSolde());
                } else {
                    oldCustomer.setSolde(oldCustomer.getSolde() + olTransaction.getSolde());
                }
                if (customerTransaction.isSoldeType()) {
                    customer.setSolde(customer.getSolde() + customerTransaction.getSolde());
                } else {
                    customer.setSolde(customer.getSolde() - customerTransaction.getSolde());
                }
                oldCustomer.setSoldeType(customer.getSolde() >= 0);
                this.customerRepository.save(oldCustomer);

            }
            customer.setSoldeType(customer.getSolde() >= 0);
            this.customerRepository.save(customer);
            olTransaction.setCustomer(customer);
            olTransaction.setDescription(customerTransaction.getDescription());
            olTransaction.setSolde(customerTransaction.getSolde());
            olTransaction.setSoldeType(customerTransaction.isSoldeType());
            olTransaction.setId(id);
            return this.customerTransactionRepository.save(olTransaction);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed");
        }
    }

}
