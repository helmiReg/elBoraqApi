package sbz.padel.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import sbz.padel.backend.entities.Provider;
import sbz.padel.backend.generics.IRepository;

public interface ProviderRepository extends IRepository<Provider> {

    @Modifying
    @Query(value = "UPDATE Provider c SET c.solde = c.solde / 1000")
    public void dinar();

    @Query(value = "SELECT sum(solde) FROM Provider")
    public double totalSolde();

    public Page<Provider> findByNameContaining(String name, Pageable pageable);

}
