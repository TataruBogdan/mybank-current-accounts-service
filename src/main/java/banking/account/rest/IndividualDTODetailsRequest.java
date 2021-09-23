package banking.account.rest;

import lombok.Data;

import java.util.Date;
@Data
public class IndividualDTODetailsRequest {

    private String firstName;
    private String lastName;
    private String address;
    private Date birthDate;
    private String phoneNumber;
    private String emailAddress;
    private String cnp;
    private String employerName;
    private String occupation;


}
