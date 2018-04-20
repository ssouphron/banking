package com.esgi.service.bank;

import com.esgi.DataFixtures;
import com.esgi.bank.BankAccount;
import com.esgi.crm.User;
import com.esgi.repository.BankAccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountServiceTest {

    @InjectMocks
    private BankAccountService sut;

    @Mock
    private BankAccountRepository bankAccountRepository;

    // TODO
}
