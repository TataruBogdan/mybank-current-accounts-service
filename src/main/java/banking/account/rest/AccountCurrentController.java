package banking.account.rest;


import banking.account.dto.AccountCurrentDTO;
import banking.account.service.AccountCurrentService;
import banking.commons.dto.IndividualDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequiredArgsConstructor
//@RequestMapping (value = "/account-current/update-balance/{iban}")
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

    //TODO return 404 - ok ?
    @GetMapping("/accounts-current/{iban}")
    public ResponseEntity<AccountCurrentDTO> retrieveAccountCurrent(@PathVariable String iban){
        Optional<AccountCurrentDTO> accountCurrentByIban = accountCurrentService.getByIban(iban);

        if (accountCurrentByIban.isPresent()) {
            IndividualDTO individualDTO = individualRestClient.getIndividualById(accountCurrentByIban.get().getIndividualId());
            accountCurrentByIban.get().setIndividual(individualDTO);
            return ResponseEntity.ok(accountCurrentByIban.get()); // return 200, with json body
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
    }



    @PatchMapping (path = "/account-current/update-balance/{iban}")
//    @ResponseBody - return type is String - not needed                                       // nu este JSON -> { "balance": 20(balance)} -> facem obiectul updateBalanceRequestDTO
    public ResponseEntity<AccountCurrentDTO> updateAccountCurrentBalance(@PathVariable("iban") String iban, @RequestBody UpdateBalanceRequestDTO amount){


            AccountCurrentDTO accountCurrentDTO = accountCurrentService.updateBalanceAccount(iban, amount.getAmount());

            return ResponseEntity.ok(accountCurrentDTO);
//            return ResponseEntity
//                    .created(URI
//                            .create(String.format("/{newBalance %s : %d}", accountCurrentDTO.getBalance())))
//                    .body(accountCurrentDTO);

    }


    @GetMapping(value = "/account-current/{individualId}")
    public ResponseEntity<List<AccountCurrentDTO>> retrieveAccountIndividual(@PathVariable("individualId") int individualId){


        List<AccountCurrentDTO> accountCurrentServiceByIndividual = accountCurrentService.getByIndividualId(individualId);

        if (accountCurrentServiceByIndividual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountCurrentServiceByIndividual);

    }


    @PostMapping(value = "/create-account/individuals/{individualId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE )
    public ResponseEntity<AccountCurrentDTO> createAccountCurrent(@PathVariable("individualId") int individualId,
                                                                  @RequestBody IndividualDTODetailsRequest individualDTODetails){

        AccountCurrentDTO individualAccount = accountCurrentService.createIndividualAccount(individualId);
//        IndividualDTO individualDTOById = individualRestClient.getIndividualById(individualId);

//        individualAccount.setIndividual(individualRestClient.getIndividualById(individualId));


        return ResponseEntity.ok(individualAccount);
    }






}
