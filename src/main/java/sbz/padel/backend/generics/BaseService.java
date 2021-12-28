package sbz.padel.backend.generics;

import sbz.padel.backend.entities.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class BaseService<T extends BaseEntity> {

    private final IRepository<T> iRepository;

    public BaseService(IRepository<T> iRepository) {
        this.iRepository = iRepository;
    }

    public Page<T> findAllFilter(boolean isActive, Pageable pageable) {
        return this.iRepository.findAllByActive(isActive, pageable);
    }

    public T findById(Long id) {
        Assert.notNull(id, this.getClass().getName() + "id should not be null");
        Optional<T> opEntity = this.iRepository.findById(id);
        return opEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, " element not existed"));
    }

    public T save(T entity) {
        return this.iRepository.save(entity);
    }

    public void deleteById(Long id) {
        Assert.notNull(id, this.getClass().getName() + "id should not be null");
        this.iRepository.deleteById(id);
    }

    public Page<T> findAllByPage(Pageable pageable) {
        return this.iRepository.findAll(pageable);
    }

    public boolean exists(Long id) {
        Assert.notNull(id, this.getClass().getName() + "id should not be null");
        return this.iRepository.existsById(id);
    }

}
