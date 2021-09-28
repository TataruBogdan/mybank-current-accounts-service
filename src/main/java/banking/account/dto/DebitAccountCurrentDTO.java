package banking.account.dto;

import lombok.Data;

@Data
public class DebitAccountCurrentDTO {

    private Double debitAmount;

    public DebitAccountCurrentDTO(){
        super();
    }

    public void debitAccountCurrent(Double debitAmount){
        this.debitAmount = debitAmount;
    }

}
