package sbz.padel.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sbz.padel.backend.entities.Customer;
import sbz.padel.backend.generics.IRepository;

public interface CustomerRepository extends IRepository<Customer> {

    Page<Customer> findAllFilterByActiveAndCin(boolean isActive, String cin, Pageable pageable);

}
