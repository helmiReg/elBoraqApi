package sbz.padel.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbz.padel.backend.entities.BankAccount;
import sbz.padel.backend.generics.BaseController;
import sbz.padel.backend.services.BankAccountService;

@RestController()
@RequestMapping("/bank-account")
public class BankAccountController extends BaseController<BankAccount> {

    final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        super(bankAccountService);
        this.bankAccountService = bankAccountService;
    }
}
