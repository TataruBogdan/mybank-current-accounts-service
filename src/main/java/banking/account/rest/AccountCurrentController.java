package banking.account.rest;


import banking.account.dto.AccountCurrentDTO;
import banking.account.service.AccountCurrentService;
import banking.commons.dto.IndividualDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
public class AccountCurrentController {


    //TODO endpoint creare cont curent: request=individual_id, response=obiectul cont current

    //TODO endpoint creditare cont curent: request=iban, amount reponse=200

    //controllerul lucreaza doar cu DTO si Service
    //service primeste DTO, si lucreaza cu repository si converteste dto->entity si invers.
    //repository lucreaza doar cu entity

    @Autowired
    private final AccountCurrentService accountCurrentService;

    @Autowired
    private IndividualRestClient individualRestClient;



    //endpoint GET all accounts
    @GetMapping("/accounts-current")
    public List<AccountCurrentDTO> retrieveAllAccounts(){
        return  accountCurrentService.getAll();
    }

    //TODO return 404 not ok - doar cand returneaza cont MODIFICAT headers nu este ok
    @GetMapping("/accounts-current/{iban}")
    public ResponseEntity<AccountCurrentDTO> retrieveAccountCurrent(@PathVariable String iban){
        Optional<AccountCurrentDTO> accountCurrentByIban = accountCurrentService.getByIban(iban);

        if (accountCurrentByIban.isPresent()) {
            IndividualDTO individualDTO = individualRestClient.getIndividualById(accountCurrentByIban.get().getIndividualId());
            accountCurrentByIban.get().setIndividual(individualDTO);
            var headers = new HttpHeaders();
            headers.add("Returned", "Account");
            return ResponseEntity.accepted().headers(headers).body(accountCurrentByIban.get());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/account-current/{individualId}")
    public ResponseEntity<List<AccountCurrentDTO>> retrieveAccountIndividual(@PathVariable("individualId") int individualId){


        List<AccountCurrentDTO> accountCurrentServiceByIndividual = accountCurrentService.getByIndividualId(individualId);

        if (accountCurrentServiceByIndividual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountCurrentServiceByIndividual);

    }



    @GetMapping(value = "/create-account/individuals/{individualId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE )
    public ResponseEntity<AccountCurrentDTO> createAccountCurrent(@PathVariable("individualId") int individualId){

        IndividualDTO individualDTOById = individualRestClient.getIndividualById(individualId);
        AccountCurrentDTO individualAccount = accountCurrentService.createIndividualAccount(individualId);

        individualAccount.setIndividual(individualDTOById);

        return ResponseEntity.ok(individualAccount);
    }






}
