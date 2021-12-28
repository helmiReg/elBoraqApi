package sbz.padel.backend.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import sbz.padel.backend.entities.base.BaseEntity;

@Entity
@SQLDelete(sql = "UPDATE provider SET is_active=false WHERE id=?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isActive", type = "boolean"))
@Filter(name = "deletedUserFilter", condition = "is_active = :isActive")
public class Provider extends BaseEntity implements Serializable {

    @Column(nullable = false, length = 64)
    private String name;
    @Column(nullable = false)
    private double solde;
    @OneToMany(mappedBy = "provider")
    @JsonIgnoreProperties(value = "provider")
    private List<Cheque> checks;

    public Provider() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public List<Cheque> getChecks() {
        return checks;
    }

    public void setChecks(List<Cheque> checks) {
        this.checks = checks;
    }

}
