package banking.account.dto;

import lombok.Data;

@Data
public class UpdateBalanceRequestDTO {

    private Double amount;

//    public UpdateBalanceRequestDTO(){
//        super();
//    }
//
//    @JsonCreator
//    public UpdateBalanceRequestDTO(@JsonProperty("amount") Double amount) {
//        this.amount = amount;
//    }
//
//    public Double getAmount() {
//        return amount;
//    }
//
//
//    @Override
//    public String toString() {
//        return "{Balance :" + amount + "}";
//    }
}
