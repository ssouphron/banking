package com.esgi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO implements Serializable {

    private Integer id;

    @Email
    private String email;

    @Length(max = 64)
    private String firstname;

    @Length(max = 64)
    private String lastname;

    @NotNull
    private LocalDate birthdate;
}
