package com.esgi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountResponseDTO implements Serializable {

    private Integer bankAccountId;

    private AmountDTO bankAccountAmount;

    private AmountDTO addedAmount;
}
