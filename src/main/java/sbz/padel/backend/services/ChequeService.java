package sbz.padel.backend.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import sbz.padel.backend.entities.Cheque;
import sbz.padel.backend.entities.Provider;
import sbz.padel.backend.generics.BaseService;
import sbz.padel.backend.repositories.ChequeRepository;
import sbz.padel.backend.repositories.ProviderRepository;

@Service
public class ChequeService extends BaseService<Cheque> {

    private final ChequeRepository chequeRepository;
    private final ProviderRepository providerRepository;

    public ChequeService(ChequeRepository chequeRepositoryitory, ProviderRepository providerRepository) {
        super(chequeRepositoryitory);
        this.chequeRepository = chequeRepositoryitory;
        this.providerRepository = providerRepository;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<Cheque> opCheque = this.chequeRepository.findById(id);
            Cheque existingCheque = opCheque.get();
            Optional<Provider> opProvider = this.providerRepository.findById(existingCheque.getProvider().getId());
            Provider provider = opProvider.get();
            if (existingCheque.isState()) {
                this.chequeRepository.delete(existingCheque);
            } else {
                provider.setSolde(provider.getSolde() - existingCheque.getSolde());
                this.providerRepository.save(provider);
                this.chequeRepository.delete(existingCheque);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " cheque introuvable");
        }
    }

    public double sumPending() {
        try {
            return this.chequeRepository.sumPending();
        } catch (Exception e) {
            return 0;
        }

    }

    public double sumPaid() {
        try {
            return this.chequeRepository.sumPaid();
        } catch (Exception e) {
            return 0;
        }

    }

    public double sum() {
        try {
            return this.chequeRepository.sum();
        } catch (Exception e) {
            return 0;
        }

    }

    public double sumPendingBetween(LocalDate fromDate, LocalDate toDate) {
        return this.chequeRepository.sumPendingBetween(fromDate, toDate);
    }

    public double sumPAidBetween(LocalDate fromDate, LocalDate toDate) {
        return this.chequeRepository.sumPAidBetween(fromDate, toDate);
    }

    public double sumBetween(LocalDate fromDate, LocalDate toDate) {
        return this.chequeRepository.sumBetween(fromDate, toDate);
    }

    public Cheque save(Cheque cheque) {
        try {
            Optional<Provider> opProvider = this.providerRepository.findById(cheque.getProvider().getId());
            Provider provider = opProvider.get();
            provider.setSolde(provider.getSolde() + cheque.getSolde());
            this.providerRepository.save(provider);
            return this.chequeRepository.save(cheque);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " fournisseur inconnu");
        }
    }

    public Cheque payCheque(Long id) {
        try {
            Optional<Cheque> opCheque = this.chequeRepository.findById(id);
            Cheque existingCheque = opCheque.get();
            Optional<Provider> opProvider = this.providerRepository.findById(existingCheque.getProvider().getId());
            Provider provider = opProvider.get();
            if (existingCheque.isState())
                return existingCheque;
            existingCheque.setState(true);
            provider.setSolde(provider.getSolde() - existingCheque.getSolde());
            this.providerRepository.save(provider);
            return this.chequeRepository.save(existingCheque);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " cheque introuvable");
        }
    }

    public Cheque getByNumber(int number) {
        try {
            Cheque existingCheque = this.chequeRepository.findByNumber(number);
            return existingCheque;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " cheque introuvable");
        }
    }

    public Page<Cheque> getByProvider(Long id, Pageable pageable) {
        try {
            Optional<Provider> opProvider = this.providerRepository.findById(id);
            Provider existingProvider = opProvider.get();
            return this.chequeRepository.findAllByProvider(existingProvider, pageable);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " fournisseur introuvable");
        }

    }

    public Page<Cheque> getByStateOrderByDateAsc(Boolean state, Pageable pageable) {
        return this.chequeRepository.findAllByStateOrderByDateAsc(state, pageable);
    }

    public Page<Cheque> getByStateBetweenOrderByDateAsc(Boolean state, LocalDate fromDate, LocalDate toDate,
            Pageable pageable) {
        return this.chequeRepository.findAllByStateAndDateBetweenOrderByDateAsc(state, fromDate, toDate, pageable);
    }

    public Page<Cheque> getBetweenOrderByDateAsc(LocalDate fromDate, LocalDate toDate,
            Pageable pageable) {
        return this.chequeRepository.findAllByDateBetweenOrderByDateAsc(fromDate, toDate, pageable);
    }

}
