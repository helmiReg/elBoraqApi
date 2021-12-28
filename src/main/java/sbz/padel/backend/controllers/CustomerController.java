package sbz.padel.backend.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbz.padel.backend.entities.Customer;
import sbz.padel.backend.generics.BaseController;
import sbz.padel.backend.services.CustomerService;

@RestController()
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer> {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        super(customerService);
        this.customerService = customerService;
    }

    @GetMapping("/all/{cin}")
    public Page<Customer> getAll(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            @PathVariable(value = "cin", required = false) String cin,
            Pageable pageable) {
        return this.customerService.findAllByActiveAndCin(isActive, cin, pageable);
    }

    @PutMapping("/{id}")
    public Customer update(@RequestBody Customer customer, @PathVariable Long id) {
        return this.customerService.update(customer, id);
    }

    @PutMapping("activate/{id}")
    public Customer restore(@PathVariable Long id) {
        return this.customerService.restoreById(id);
    }
}
