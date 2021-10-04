package banking.account.dto;

import lombok.Data;

@Data
public class CreditAccountCurrentDTO {

    private Double creditAmount;


    public void creditAccountCurrent(Double creditAmount){
        this.creditAmount = creditAmount;
    }


}
