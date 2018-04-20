package com.esgi.bank;

import com.esgi.crm.User;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class BankAccount {

    @Id
    @Column(name = "bank_account_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"))
    @Cascade(CascadeType.PERSIST)
    private User owner;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public boolean maximumAmountReached() {
        return amount == 1000;
    }
}
