package banking.account.rest;

import lombok.Data;

@Data
public class DebitAccountCurrent {

    private Double debitAmount;

    public DebitAccountCurrent(){
        super();
    }

    public void debitAccountCurrent(Double debitAmount){
        this.debitAmount = debitAmount;
    }

}
