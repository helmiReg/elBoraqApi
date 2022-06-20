package sbz.padel.backend.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
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

    public double totalSolde() {
        return this.providerRepository.totalSolde();
    }

    public Page<Provider> findByName(String name, int pageNo, int pageSize, String sortBy, String sortDir,
            boolean isActive) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        if (!StringUtils.hasText(name)) {
            return this.providerRepository.findAllByActive(isActive, PageRequest.of(pageNo, pageSize, sort));
        }
        return this.providerRepository.findByNameContainingAndActive(name, PageRequest.of(pageNo, pageSize, sort), isActive);
    }

}
