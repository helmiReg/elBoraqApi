package sbz.padel.backend.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbz.padel.backend.entities.CustomerInvoice;
import sbz.padel.backend.generics.BaseController;
import sbz.padel.backend.services.CustomerInvoiceService;

@RestController()
@RequestMapping("/customer-invoice")
public class CustomerInvoiceController extends BaseController<CustomerInvoice> {
    private final CustomerInvoiceService customerInvoiceService;

    public CustomerInvoiceController(CustomerInvoiceService customerInvoiceService) {
        super(customerInvoiceService);
        this.customerInvoiceService = customerInvoiceService;
    }

    @PutMapping("/{id}")
    public CustomerInvoice update(@RequestBody CustomerInvoice customerInvoice, @PathVariable Long id) {
        return this.customerInvoiceService.update(customerInvoice, id);
    }
}
