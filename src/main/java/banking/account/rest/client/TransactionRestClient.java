package banking.account.rest.client;


import banking.commons.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionRestClient {

    @Autowired
    RestTemplate restTemplate;

    public TransactionDTO getTransaction(String fromIban, String toIban){

        return restTemplate.getForObject("http://localhost:8500/transactions/fromIban/" + fromIban + "/toIban/" + toIban, TransactionDTO.class);
    }

}
