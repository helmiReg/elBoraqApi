package sbz.padel.backend.generics;

import sbz.padel.backend.entities.base.BaseEntity;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
public class BaseController<T extends BaseEntity> {
    private final BaseService<T> baseService;

    public BaseController(BaseService<T> baseService) {
        this.baseService = baseService;
    }

    @PostMapping("/add")
    public T add(@Valid @RequestBody T entity, Errors errors) {

        if (errors.hasErrors()) {
            throw new ValidationException("veuillez entrer des données valides!");
        }
        return this.baseService.save(entity);
    }

    @GetMapping("/all")
    public Page<T> getAll(@RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive,
            Pageable pageable) {
        return this.baseService.findAllFilter(isActive, pageable);
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable Long id) {
        return this.baseService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.baseService.deleteById(id);
    }
}
