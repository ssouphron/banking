package com.esgi.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AmountDTO implements Serializable {

    @NotNull
    private Integer amount;
}
