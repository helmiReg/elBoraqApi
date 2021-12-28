package sbz.padel.backend.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sbz.padel.backend.entities.base.Invoice;

@Entity
public class CustomerInvoice extends Invoice implements Serializable {
    @ManyToOne
    @JsonIgnoreProperties(value = "invoices")
    private Customer customer;

    public CustomerInvoice() {
    }

    public CustomerInvoice(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
