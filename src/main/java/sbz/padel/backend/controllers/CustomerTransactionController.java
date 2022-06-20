package sbz.padel.backend.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbz.padel.backend.entities.CustomerTransaction;
import sbz.padel.backend.generics.BaseController;
import sbz.padel.backend.services.CustomerTransactionService;

@RestController()
@RequestMapping("/transaction")
public class CustomerTransactionController extends BaseController<CustomerTransaction> {
    private final CustomerTransactionService customerTransactionService;
    private final DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();

    public CustomerTransactionController(CustomerTransactionService customerTransactionService) {
        super(customerTransactionService);
        this.customerTransactionService = customerTransactionService;
    }

    @GetMapping("/all/recent")
    public Page<CustomerTransaction> findAllByCreatedAtDesc(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            Pageable pageable) {
        return this.customerTransactionService.findAllDesc(isActive, pageable);
    }

    @GetMapping("/customer/recent/{id}/")
    public Page<CustomerTransaction> findAllByCustomerCreatedAtDesc(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            @PathVariable(value = "id") Long id,
            Pageable pageable) {
        return this.customerTransactionService.findAllByCustomerCreatedAtDesc(isActive, id, pageable);
    }

    @GetMapping("/all/between")
    public Page<CustomerTransaction> findAllByCreatedAtBetween(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            @RequestParam(value = "fromDate") String fromDate,
            @RequestParam(value = "toDate") String toDate,
            Pageable pageable) {
        LocalDate newFromDate = LocalDate.parse(fromDate, this.df);
        LocalDate newToDate = LocalDate.parse(toDate, this.df);
        return this.customerTransactionService.findAllBetween(isActive, newFromDate, newToDate, pageable);
    }

    @GetMapping("/customer/between/{id}/")
    public Page<CustomerTransaction> findAllByCustomerByCreatedAtBetween(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            @RequestParam(value = "fromDate") String fromDate,
            @RequestParam(value = "toDate") String toDate,
            @PathVariable(value = "id") Long id,
            Pageable pageable) {

        LocalDate newFromDate = LocalDate.parse(fromDate, this.df);
        LocalDate newToDate = LocalDate.parse(toDate, this.df);
        return this.customerTransactionService.findAllByCustomerDateBetween(isActive, newFromDate, newToDate, id,
                pageable);
    }

    @GetMapping("/customer/{id}/debit/")
    public Page<CustomerTransaction> findAllByCustomerDebit(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            @PathVariable(value = "id") Long id,
            Pageable pageable) {
        return this.customerTransactionService.findAllByCustomerDebit(isActive, id,
                pageable);
    }

    @GetMapping("/customer/{id}/")
    public Page<CustomerTransaction> findAllByCustomer(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            @PathVariable(value = "id") Long id,
            Pageable pageable) {
        return this.customerTransactionService.findAllByCustomer(isActive, id,
                pageable);
    }

    @GetMapping("/customer/{id}/credit/")
    public Page<CustomerTransaction> findAllByCustomerCredit(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            @PathVariable(value = "id") Long id,
            Pageable pageable) {
        return this.customerTransactionService.findAllByCustomerCredit(isActive, id,
                pageable);
    }

    @PutMapping("/{id}")
    public CustomerTransaction update(@RequestBody CustomerTransaction customerTransaction, @PathVariable Long id) {
        return this.customerTransactionService.update(customerTransaction, id);
    }

}
