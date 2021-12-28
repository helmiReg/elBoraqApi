package sbz.padel.backend.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Provider> getByName(@RequestParam String name, Pageable pageable) {
        return this.providerService.findByName(name, pageable);
    }

}