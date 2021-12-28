package sbz.padel.backend.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import sbz.padel.backend.entities.base.BaseEntity;

@Entity
@SQLDelete(sql = "UPDATE customer SET is_active=false WHERE id=?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isActive", type = "boolean"))
@Filter(name = "deletedUserFilter", condition = "is_active = :isActive")
public class Customer extends BaseEntity implements Serializable {
    @Column(unique = true, length = 32, nullable = false)
    @Size(min = 8, message = "Veuillez entrer un numéro de CIN valide!")
    private String cin;
    @Column(nullable = false, length = 64)
    @Size(min = 4, message = "Veuillez entrer le nom complet!")
    private String name;
    @Column(nullable = false, length = 64)
    private String address;
    @Column(nullable = false, length = 32)
    @Size(min = 8, message = "Veuillez entrer un numéro de téléphone valide!")
    @Pattern(regexp = "[0-9]+", message = "Veuillez entrer un numéro de téléphone valide!")
    private String telephone;
    @Column(nullable = false, length = 64)
    private String guarantee;
    @Column(length = 64)
    private String bank;
    @Column(length = 64)
    private String bankAccount;
    @Column(nullable = false)
    private double solde;
    @Column(nullable = false)
    private double soldeInitial;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean soldeType;
    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = "customer")
    private List<CustomerInvoice> invoices;
    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = "customer")
    private List<CustomerTransaction> transactions;

    public Customer() {
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
        if (this.solde < 0) {
            this.soldeType = false;
        }
    }

    public double getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(double soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public boolean isSoldeType() {
        return soldeType;
    }

    public void setSoldeType(boolean soldeType) {
        this.soldeType = soldeType;
    }

    public List<CustomerInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<CustomerInvoice> invoices) {
        this.invoices = invoices;
    }

    public List<CustomerTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<CustomerTransaction> transactions) {
        this.transactions = transactions;
    }

}
