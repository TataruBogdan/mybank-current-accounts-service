package banking.account.rest;


import banking.account.dto.AccountCurrentDTO;
import banking.account.service.AccountCurrentService;
import banking.commons.dto.IndividualDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    //endpoint creditare cont curent: request=iban, amount reponse=200

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

    //endpoint GET cont dupa IBAN
    //NU SE MAPEAZA PE ID ?
    @GetMapping("/accounts-current/{iban}")
    public ResponseEntity<AccountCurrentDTO> retrieveAccountCurrent(@PathVariable String iban){
        Optional<AccountCurrentDTO> accountCurrentById = accountCurrentService.getByIban(iban);

        if (accountCurrentById.isPresent()){
            IndividualDTO individualDTO = individualRestClient.getIndividualById(accountCurrentById.get().getIndividualId());
            accountCurrentById.get().setIndividual(individualDTO);
            return ResponseEntity.ok(accountCurrentById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value = "/create-account-current/individual/{individualId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE )
    public ResponseEntity<AccountCurrentDTO> createAccountCurrent(@PathVariable("individualId") int individualId){
        AccountCurrentDTO individualAccount = accountCurrentService.createIndividualAccount(individualId);
        return ResponseEntity.ok(individualAccount);
    }




}
