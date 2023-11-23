package sbz.padel.backend.services;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import sbz.padel.backend.config.RestServerResponseException;
import sbz.padel.backend.entities.BankAccount;
import sbz.padel.backend.entities.Cheque;
import sbz.padel.backend.entities.Provider;
import sbz.padel.backend.generics.BaseService;
import sbz.padel.backend.repositories.BankAccountRepository;
import sbz.padel.backend.repositories.ChequeRepository;
import sbz.padel.backend.repositories.ProviderRepository;

@Service
public class ChequeService extends BaseService<Cheque> {

    private final ChequeRepository chequeRepository;
    private final ProviderRepository providerRepository;
    private final BankAccountRepository bankAccountRepository;

    public ChequeService(ChequeRepository chequeRepositoryitory, ProviderRepository providerRepository,
            BankAccountRepository bankAccountRepository) {
        super(chequeRepositoryitory);
        this.chequeRepository = chequeRepositoryitory;
        this.providerRepository = providerRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Cheque existingCheque = this.chequeRepository.findById(id)
                .orElseThrow(() -> new RestServerResponseException(HttpStatus.NOT_FOUND, "cheque introuvable!"));
        Provider provider = this.providerRepository.findById(existingCheque.getProvider().getId())
                .orElseThrow(() -> new RestServerResponseException(HttpStatus.NOT_FOUND, "Fournisseur introuvable!"));
        if (existingCheque.isState()) {
            this.chequeRepository.delete(existingCheque);
        } else {
            provider.setSolde(provider.getSolde() - existingCheque.getSolde());
            this.providerRepository.save(provider);
            this.chequeRepository.delete(existingCheque);
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
        return this.chequeRepository.sumPaid();
    }

    public double sum() {
        return this.chequeRepository.sum();

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

    @Transactional
    public Cheque save(Cheque cheque) {
        Provider provider = this.providerRepository.findById(cheque.getProvider().getId())
                .orElseThrow(() -> new RestServerResponseException(HttpStatus.NOT_FOUND, "Fournisseur introuvable!"));
        provider.setSolde(provider.getSolde() + cheque.getSolde());
        BankAccount bankAccount = this.bankAccountRepository.findById(cheque.getBankAccount().getId()).orElseThrow(
                () -> new RestServerResponseException(HttpStatus.NOT_FOUND, "compte bancaire introuvable!"));
        this.providerRepository.save(provider);
        return this.chequeRepository.save(cheque);
    }

    public Cheque payCheque(Long id) {
        Cheque existingCheque = this.chequeRepository.findById(id)
                .orElseThrow(() -> new RestServerResponseException(HttpStatus.NOT_FOUND, "Cheque introuvable!"));
        Provider provider = this.providerRepository.findById(existingCheque.getProvider().getId())
                .orElseThrow(() -> new RestServerResponseException(HttpStatus.NOT_FOUND, "Fournisseur introuvable!"));
        if (existingCheque.isState())
            return existingCheque;
        existingCheque.setState(true);
        provider.setSolde(provider.getSolde() - existingCheque.getSolde());
        this.providerRepository.save(provider);
        return this.chequeRepository.save(existingCheque);
    }

    public Cheque getByNumber(int number) {
        Cheque existingCheque = this.chequeRepository.findByNumber(number)
                .orElseThrow(() -> new RestServerResponseException(HttpStatus.NOT_FOUND,
                        "Cheque de numéro #" + number + " est introuvable!"));
        ;
        return existingCheque;
    }

    public Page<Cheque> getByProvider(Long id, Pageable pageable) {
        Provider existingProvider = this.providerRepository.findById(id)
                .orElseThrow(() -> new RestServerResponseException(HttpStatus.NOT_FOUND, "Fournisseur introuvable!"));
        return this.chequeRepository.findAllByProvider(existingProvider, pageable);
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
