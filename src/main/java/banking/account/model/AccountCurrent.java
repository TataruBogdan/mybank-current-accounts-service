package banking.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "account_current")
public class AccountCurrent {

    //define our generator for ???@GeneratedValue
    @Id
    @NotNull
    @Column(name = "iban", unique = true)
    private String iban;

    private Double balance;
    private Integer individual_id;
    private Date startDate;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;

    private boolean primaryAccount;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;


}
