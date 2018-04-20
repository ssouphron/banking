package com.esgi.crm;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    public boolean isValid() {
        return isNotBlank(this.firstname)
                && isNotBlank(this.lastname)
                && isAdult();
    }

    protected boolean isAdult() {
        return this.birthdate != null
                && birthdate.plusYears(18).isBefore(now());
    }

}
