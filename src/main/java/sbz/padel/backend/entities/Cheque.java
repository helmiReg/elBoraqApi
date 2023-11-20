package sbz.padel.backend.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import sbz.padel.backend.entities.base.BaseEntity;

@Entity
@SQLDelete(sql = "UPDATE cheque SET is_active=false WHERE id=?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isActive", type = "boolean"))
@Filter(name = "deletedUserFilter", condition = "is_active = :isActive")
public class Cheque extends BaseEntity implements Serializable {
    @Column(nullable = false, unique = true)
    private int number;
    @Column(nullable = false)
    private double solde;
    @ManyToOne
    @JsonIgnoreProperties(value = "cheques")
    private Provider provider;
    @ManyToOne
    @JsonIgnoreProperties(value = "cheques")
    private BankAccount bankAccount;
    @Column(nullable = false)
    private LocalDate date;
    // pending=0 /// paid=1
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean state;

    public Cheque() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

}
