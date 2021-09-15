package banking.account.rest;


import banking.account.dto.AccountCurrentDTO;
import banking.account.service.AccountCurrentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/create-account-current/individual_id", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountCurrentController {

    //TODO endpoint creare cont curent: request=individual_id, response=obiectul cont current

    //endpoint creditare cont curent: request=iban, amount reponse=200

    //controllerul lucreaza doar cu DTO si Service
    //service primeste DTO, si lucreaza cu repository si converteste dto->entity si invers.
    //repository lucreaza doar cu entity

    @Autowired
    private final AccountCurrentService accountCurrentService;

    //endpoint GET all accounts
    @GetMapping("/accounts-current")
    public List<AccountCurrentDTO> retrieveAllAccounts(){
        return  accountCurrentService.getAll();
    }

    //endpoint GET cont dupa IBAN
    //NU SE MAPEAZA PE ID ?
    @GetMapping("/accounts-current/{iban}")
    public ResponseEntity<AccountCurrentDTO> retrieveAccountCurrent(@PathVariable String iban){
        Optional<AccountCurrentDTO> accountCurrentById = accountCurrentService.getById(iban);


        if (accountCurrentById.isPresent()){

            //TODO 6) call REST la microserviciul /individuals/{id} care retunreaza un IndividualDTO; apelul IndividualsRestClient.getIndividualById(id)
            //pe care il bag in AccountCurrentDTO si returnez AccountCurrentDTO care acum continue toate datele despre individual

            //TODO 5) call REST cu RestTemplate....adica o clasa noua IndividualsRestClient in care se foloseste RestsTemplate sa apelezi serviciul clients


            return ResponseEntity.ok(accountCurrentById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping(value = "/accounts-current/create-account/{individualId}" )
//    public ResponseEntity<AccountCurrentDTO> createAccountCurrent(@PathVariable Integer individualId){
//
//        accountCurrentService.save(accountCurrentService.);
//
//
//    }



}
