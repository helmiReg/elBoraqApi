package sbz.padel.backend.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbz.padel.backend.entities.Provider;
import sbz.padel.backend.generics.BaseController;
import sbz.padel.backend.services.ProviderService;

@RestController()
@RequestMapping("/provider")
public class ProviderController extends BaseController<Provider> {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        super(providerService);
        this.providerService = providerService;
    }
    /*
     * /1000
     * 
     * @PutMapping("/dinar")
     * public void dinar() {
     * this.providerService.dinar();
     * }
     */

    @GetMapping("/totalSolde")
    public double total() {
        return this.providerService.totalSolde();
    }

    @GetMapping("")
    public Page<Provider> getByName(
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            @RequestParam(defaultValue = "") String name, @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "solde") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        if (StringUtils.hasText(name)) {
            return this.providerService.findByName(name, page, size, sortBy, sortDirection, isActive);
        }
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.providerService.findAllFilter(isActive, pageable);

    }

}
