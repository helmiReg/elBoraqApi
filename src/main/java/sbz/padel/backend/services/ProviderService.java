package sbz.padel.backend.services;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sbz.padel.backend.entities.Provider;
import sbz.padel.backend.generics.BaseService;
import sbz.padel.backend.repositories.ProviderRepository;

@Service
public class ProviderService extends BaseService<Provider> {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        super(providerRepository);
        this.providerRepository = providerRepository;
    }

    @Transactional
    public void dinar() {
        this.providerRepository.dinar();
    }

    public double totalSolde() {
        return this.providerRepository.totalSolde();
    }

    public Page<Provider> findByName(String name, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        if (name == "") {
            return this.providerRepository.findAll(pageable);
        } else
            return this.providerRepository.findByNameContaining(name, pageable);
    }

}
