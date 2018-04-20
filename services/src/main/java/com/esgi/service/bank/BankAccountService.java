package com.esgi.service.bank;

import com.esgi.repository.BankAccountRepository;
import com.esgi.bank.BankAccount;
import com.esgi.crm.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.Assert.notNull;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Create a new bank account, for the specified user
     *
     * @param user the bank account owner
     * @param initialAmount the initial bankAccountAmount, between 0 and 1000
     * @return the added amount and the bank account new amount
     * @throws Exception
     */
    public CreditBankAccountData createBankAccount(User user, int initialAmount) throws Exception {
        if (user == null || !user.isValid()) {
            throw new Exception("Bad user: User is null or not valid");
        }

        BankAccount bankAccount = BankAccount.builder()
                .owner(user)
                .amount(0)
                .build();

        return safeCredit(bankAccount, initialAmount);
    }

    /**
     * Credit the bank account. The bank account cannot be greater than 1000
     *
     * @param bankAccount the bank account to credit
     * @param amount the amount to credit, between 0 and 1000
     * @return the added amount and the bank account new amount
     * @throws Exception
     */
    public CreditBankAccountData credit(BankAccount bankAccount, int amount) throws Exception {
        notNull(bankAccount, "Bank account must not be null");

        User owner = bankAccount.getOwner();
        if (owner == null || !owner.isValid()) {
            throw new Exception("Bad user: User is null or not valid");
        }

        return safeCredit(bankAccount, amount);
    }

    /**
     * Bank account must not be negative and must not be greater than 1000
     *
     * @param bankAccount the bank account to credit
     * @param amount the amount to credit
     * @return the added amount and the bank account new amount
     * @throws Exception
     */
    protected CreditBankAccountData safeCredit(BankAccount bankAccount, int amount) throws Exception {
        notNull(bankAccount, "Bank account must not be null");

        if (amount < 0) {
            throw new Exception("Bad bankAccountAmount: bad bankAccountAmount parameter");
        }

        int initialAmount = bankAccount.getAmount();
        int newAmount = Math.min(initialAmount + amount, 1000);
        int addedAmount = amount + bankAccount.getAmount() <= 1000 ? amount : newAmount - initialAmount;

        bankAccount.setAmount(newAmount);
        bankAccountRepository.save(bankAccount);

        return CreditBankAccountData.builder()
                .addedAmount(addedAmount)
                .newBankAccount(newAmount)
                .build();
    }

    /**
     * Get user bank account list
     * @param user the user to whom we want to list all bank account
     * @return user bank account list
     */
    public List<BankAccount> getUserAccounts(User user) {
        notNull(user, "User must not be null");

        return bankAccountRepository.findByOwnerId(user.getId());
    }

    /**
     * Get user specific bank account
     * @param user the user to whom we want to list all bank account
     * @param bankAccountId the id of the bank account
     * @return the user bank account
     * @throws Exception
     */
    public BankAccount getUserBankAccount(User user, Integer bankAccountId) throws Exception {
        notNull(user, "User must not be null");

        BankAccount bankAccount = bankAccountRepository.getOne(bankAccountId);
        if (!bankAccount.getOwner().getId().equals(user.getId())) {
            throw new Exception("User is not the owner of requested bank account");
        }

        return bankAccount;
    }
}
