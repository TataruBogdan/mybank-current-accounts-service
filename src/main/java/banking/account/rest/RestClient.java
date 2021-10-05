package banking.account.rest;

import banking.commons.dto.IndividualDTO;
import banking.commons.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;


    public IndividualDTO getIndividualById(Integer id) {

                                                                     //retrieve id from Individual and response is converted and returned
        IndividualDTO individualDTO = restTemplate.getForObject("http://localhost:8100/individuals/" + id, IndividualDTO.class);

        return individualDTO;
    }


    public TransactionDTO getTransactionById(String transactionId){

        TransactionDTO transactionDTO = restTemplate.getForObject("http://localhost:8500/transactions/" + transactionId, TransactionDTO.class);

        return transactionDTO;

    }

}
