package sbz.padel.backend.generics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import sbz.padel.backend.entities.base.BaseEntity;

@NoRepositoryBean
public interface IRepository<T extends BaseEntity> extends PagingAndSortingRepository<T, Long> {
    abstract public Page<T> findAllByActive(boolean isActive, Pageable pageable);

    abstract public Page<T> findAll(Pageable pageable);
}
