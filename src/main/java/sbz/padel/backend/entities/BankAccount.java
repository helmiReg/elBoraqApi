package sbz.padel.backend.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sbz.padel.backend.entities.base.BaseEntity;

@Entity
@SQLDelete(sql = "UPDATE bank_account SET is_active=false WHERE id=?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isActive", type = "boolean"))
@Filter(name = "deletedUserFilter", condition = "is_active = :isActive")
public class BankAccount extends BaseEntity implements Serializable {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String rib;

    public BankAccount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

}
