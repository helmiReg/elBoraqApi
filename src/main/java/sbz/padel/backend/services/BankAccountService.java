package sbz.padel.backend.services;

import org.springframework.stereotype.Service;

import sbz.padel.backend.entities.BankAccount;
import sbz.padel.backend.generics.BaseService;
import sbz.padel.backend.repositories.BankAccountRepository;

@Service
public class BankAccountService extends BaseService<BankAccount> {
    final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        super(bankAccountRepository);
        this.bankAccountRepository = bankAccountRepository;
        // TODO Auto-generated constructor stub
    }

}
