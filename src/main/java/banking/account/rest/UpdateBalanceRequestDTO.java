package banking.account.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateBalanceRequestDTO {

    private Double amount;

    public UpdateBalanceRequestDTO(){
        super();
    }

    @JsonCreator
    public UpdateBalanceRequestDTO(@JsonProperty("amount") Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }


    @Override
    public String toString() {
        return "{Balance :" + amount + "}";
    }
}
