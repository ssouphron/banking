package com.esgi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankAccountCreationRequestDTO implements Serializable {

    @Valid
    private UserDTO user;

    @Valid
    private AmountDTO initialAmount;
}
