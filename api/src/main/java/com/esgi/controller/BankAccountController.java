package com.esgi.controller;

import com.esgi.bank.BankAccount;
import com.esgi.crm.User;
import com.esgi.dto.AmountDTO;
import com.esgi.dto.BankAccountCreationRequestDTO;
import com.esgi.dto.BankAccountResponseDTO;
import com.esgi.dto.UserBankAccountListResponseDTO;
import com.esgi.error.BadRequestException;
import com.esgi.error.NotFoundException;
import com.esgi.helper.DTOHelper;
import com.esgi.service.bank.BankAccountService;
import com.esgi.service.bank.CreditBankAccountData;
import com.esgi.service.crm.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final UserService userService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, UserService userService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;
    }

    /**
     * Create bank account for a new user
     */
    @RequestMapping(method = POST, value = "/banks", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public BankAccountResponseDTO createBankAccountForNewUser(@RequestBody @Valid final BankAccountCreationRequestDTO requestDTO) throws Exception {
        if (requestDTO.getUser() == null) {
            throw new BadRequestException();
        }

        User user = User.builder()
                .email(requestDTO.getUser().getEmail())
                .firstname(requestDTO.getUser().getFirstname())
                .lastname(requestDTO.getUser().getLastname())
                .birthdate(requestDTO.getUser().getBirthdate())
                .build();

        userService.saveNewUser(user);
        CreditBankAccountData creditBankAccountData = bankAccountService.createBankAccount(user, requestDTO.getInitialAmount().getAmount());

        return buildBankAccountResponse(creditBankAccountData);
    }

    /**
     * Create bank account for an existing user
     */
    @RequestMapping(method = POST, value = "/users/{id}/banks", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public BankAccountResponseDTO createBankAccountForExistingUser(@PathVariable(value = "id") final Integer userId,
                                                                   @RequestBody @Valid final AmountDTO initialAmount) throws Exception {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        CreditBankAccountData creditBankAccountData = bankAccountService.createBankAccount(user, initialAmount.getAmount());
        return buildBankAccountResponse(creditBankAccountData);
    }

    /**
     * Credit user bank account
     */
    @RequestMapping(method = PUT, value = "/users/{user_id}/banks/{bank_account_id}")
    @ResponseStatus(OK)
    public BankAccountResponseDTO creditUserAccount(@PathVariable(value = "user_id") final Integer userId,
                                                    @PathVariable(value = "bank_account_id") final Integer bankAccountId,
                                                    @RequestBody @Valid final AmountDTO amount) throws Exception {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        BankAccount bankAccount = getSafeBankAccount(bankAccountId, user);
        CreditBankAccountData creditBankAccountData = bankAccountService.credit(bankAccount, amount.getAmount());

        return buildBankAccountResponse(creditBankAccountData);
    }

    /**
     * Get user specific bank account
     */
    @RequestMapping(method = GET, value = "/users/{user_id}/banks/{bank_account_id}")
    @ResponseStatus(OK)
    public BankAccountResponseDTO getUserAccount(@PathVariable(value = "user_id") final Integer userId,
                                                 @PathVariable(value = "bank_account_id") final Integer bankAccountId) throws Exception {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        BankAccount bankAccount = getSafeBankAccount(bankAccountId, user);

        return DTOHelper.getBankAccountDTO(bankAccount);
    }

    /**
     * Get user account list
     */
    @RequestMapping(method = GET, value = "/users/{id}/banks")
    @ResponseStatus(OK)
    public UserBankAccountListResponseDTO getUserAccounts(@PathVariable(value = "id") final Integer userId) throws NotFoundException {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        List<BankAccount> bankAccountList = bankAccountService.getUserAccounts(user);

        return DTOHelper.getUserBankAccountListResponseDTO(bankAccountList);
    }

    // ****************//
    // PRIVATE METHODS //
    // *************** //

    private BankAccountResponseDTO buildBankAccountResponse(CreditBankAccountData creditBankAccountData) {
        return BankAccountResponseDTO.builder()
                .addedAmount(AmountDTO.builder().amount(creditBankAccountData.getAddedAmount()).build())
                .bankAccountAmount(AmountDTO.builder().amount(creditBankAccountData.getNewBankAccount()).build())
                .build();
    }

    private BankAccount getSafeBankAccount(Integer bankAccountId, User user) throws NotFoundException {
        BankAccount bankAccount;

        try {
            bankAccount = bankAccountService.getUserBankAccount(user, bankAccountId);
        } catch (Exception e) {
            throw new NotFoundException();
        }
        return bankAccount;
    }
}
