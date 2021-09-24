package banking.account.rest;

import lombok.Data;

@Data
public class CreditAccountCurrent {

    private Double creditAmount;

    public CreditAccountCurrent(){
        super();
    }

    public void creditAccountCurrent(Double creditAmount){
        this.creditAmount = creditAmount;
    }


//    @Override
//    public String toString() {
//        return "{Balance :" + creditAmount + "}";
//    }

}
