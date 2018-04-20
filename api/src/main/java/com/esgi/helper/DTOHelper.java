package com.esgi.helper;

import com.esgi.bank.BankAccount;
import com.esgi.crm.User;
import com.esgi.dto.AmountDTO;
import com.esgi.dto.BankAccountResponseDTO;
import com.esgi.dto.UserBankAccountListResponseDTO;
import com.esgi.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOHelper {

    public static UserDTO getUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .birthdate(user.getBirthdate())
                .build();
    }

    public static List<UserDTO> getUserDTO(List<User> userList) {
        List<UserDTO> result = new ArrayList<>(userList.size());

        userList.forEach(user -> result.add(getUserDTO(user)));

        return result;
    }

    public static BankAccountResponseDTO getBankAccountDTO(BankAccount bankAccount) {
        return BankAccountResponseDTO.builder()
                .bankAccountId(bankAccount.getId())
                .bankAccountAmount(new AmountDTO(bankAccount.getAmount()))
                .addedAmount(null)
                .build();
    }

    public static UserBankAccountListResponseDTO getUserBankAccountListResponseDTO(List<BankAccount> bankAccountList) {
        List<BankAccountResponseDTO> result = new ArrayList<>(bankAccountList.size());

        bankAccountList.forEach(bankAccount -> result.add(getBankAccountDTO(bankAccount)));

        return new UserBankAccountListResponseDTO(result);
    }
}
