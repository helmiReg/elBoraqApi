package sbz.padel.backend.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sbz.padel.backend.entities.Cheque;
import sbz.padel.backend.entities.Provider;
import sbz.padel.backend.generics.IRepository;

public interface ChequeRepository extends IRepository<Cheque> {

        @Query(value = "SELECT sum(solde) FROM Cheque where state=false")
        public double sumPending();

        @Query(value = "SELECT sum(solde) FROM Cheque where state=false And date BETWEEN :fromDate And :toDate")
        public double sumPendingBetween(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

        @Query(value = "SELECT sum(solde) FROM Cheque where state=true")
        public double sumPaid();

        @Query(value = "SELECT sum(solde) FROM Cheque where state=true And date BETWEEN :fromDate And :toDate")
        public double sumPAidBetween(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

        @Query(value = "SELECT sum(solde) FROM Cheque")
        public double sum();

        @Query(value = "SELECT sum(solde) FROM Cheque where date BETWEEN :fromDate And :toDate")
        public double sumBetween(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

        public Optional<Cheque> findByNumber(int number);

        public Cheque findByProvider(Provider provider);

        public Page<Cheque> findAllByProvider(Provider provider, Pageable pageable);

        public Page<Cheque> findAllByStateOrderByDateAsc(Boolean state, Pageable pageable);

        public Page<Cheque> findAllByStateAndDateBetweenOrderByDateAsc(Boolean state, LocalDate fromDate,
                        LocalDate toDate,
                        Pageable pageable);

        public Page<Cheque> findAllByDateBetweenOrderByDateAsc(LocalDate fromDate, LocalDate toDate,
                        Pageable pageable);

}
