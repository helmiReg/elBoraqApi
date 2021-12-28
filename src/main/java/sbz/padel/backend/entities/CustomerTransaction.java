package sbz.padel.backend.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sbz.padel.backend.entities.base.BaseEntity;

@Entity
public class CustomerTransaction extends BaseEntity implements Serializable {
    @Column(nullable = false)
    private double solde;
    @Column(nullable = false)
    private boolean soldeType;
    @Column(nullable = false, length = 128)
    private String description;
    @ManyToOne
    @JsonIgnoreProperties(value = "transactions")
    private Customer customer;

    public CustomerTransaction() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public boolean isSoldeType() {
        return soldeType;
    }

    public void setSoldeType(boolean soldeType) {
        this.soldeType = soldeType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
