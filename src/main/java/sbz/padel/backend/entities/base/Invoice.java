package sbz.padel.backend.entities.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Invoice extends BaseEntity {
    @Column(nullable = false)
    private double solde;
    @Column(nullable = false, length = 128)
    private String description;

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
