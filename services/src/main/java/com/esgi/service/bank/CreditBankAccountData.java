package com.esgi.service.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreditBankAccountData {

    private Integer newBankAccount;

    private  Integer addedAmount;
}
