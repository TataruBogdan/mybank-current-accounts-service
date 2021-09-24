package banking.account.dto;

import banking.account.model.CurrentStatus;
import banking.commons.dto.IndividualDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class AccountCurrentDTO {

    @NotNull
    private String iban;
    private Double balance;
    private Integer individualId;
    private Date startDate;
    private CurrentStatus currentStatus;
    private boolean primaryAccount;
    private IndividualDTO individual;



    // TODO 4) private IndividualDTO individual;
    //TODO 1) creezi un proiect nou mubank-commons  in care sa strangem toate DTO urile folosite la comun de toate microserviciile
    //TODO 2) dai mvn clean install
    //TODO 3) pe care il vom importa in fiecare moicroserviciu in pom.xml ca dependency
//
}
