package sbz.padel.backend.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sbz.padel.backend.entities.Customer;
import sbz.padel.backend.entities.CustomerTransaction;
import sbz.padel.backend.generics.IRepository;

public interface CustomerTransactionRepository extends IRepository<CustomerTransaction> {
        Page<CustomerTransaction> findAllFilterByActiveOrderByCreatedAtDesc(boolean isActive, Pageable pageable);

        Page<CustomerTransaction> findAllFilterByActiveAndCreatedAtBetween(boolean isActive, LocalDate fromDate,
                        LocalDate toDate, Pageable pageable);

        Page<CustomerTransaction> findAllFilterByActiveAndCustomerAndCreatedAtBetween(boolean isActive,
                        Customer customer, LocalDate fromDate, LocalDate toDate,
                        Pageable pageable);

        Page<CustomerTransaction> findAllFilterByActiveAndCustomer(boolean isActive, Customer customer,
                        Pageable pageable);

        Page<CustomerTransaction> findAllFilterByActiveAndCustomerOrderByCreatedAtDesc(boolean isActive,
                        Customer existingCustomer,
                        Pageable pageable);

        Page<CustomerTransaction> findAllFilterByActiveAndCustomerAndSoldeType(boolean isActive,
                        Customer existingCustomer,
                        boolean isSoldeType,
                        Pageable pageable);

}
