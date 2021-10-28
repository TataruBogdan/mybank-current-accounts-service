package banking.account.rest;


import banking.commons.dto.CreditAccountCurrentDTO;
import banking.commons.dto.DebitAccountCurrentDTO;
import banking.account.dto.UpdateBalanceRequestDTO;
import banking.account.rest.client.IndividualRestClient;
import banking.account.service.AccountCurrentService;
import banking.commons.dto.AccountCurrentDTO;
import banking.commons.dto.IndividualDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<AccountCurrentDTO>> retrieveAllAccounts(){

        List<AccountCurrentDTO> allAccountCurrentDTOS = accountCurrentService.getAll();
        if (allAccountCurrentDTOS.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<AccountCurrentDTO> newAccountCurrentDTOList = new ArrayList<>();
        for (AccountCurrentDTO accountCurrentDTO: allAccountCurrentDTOS) {
            IndividualDTO individualById = individualRestClient.getIndividualById(accountCurrentDTO.getIndividualId());
            accountCurrentDTO.setIndividual(individualById);
            newAccountCurrentDTOList.add(accountCurrentDTO);
        }
        return ResponseEntity.ok(newAccountCurrentDTOList);
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
    }

    //Receive into account
    //modify AccountCurrent to be credited with amount from the transaction,
    // transaction must be made from this account to this account
    // if the transaction is made fromIban then it's get debited from fromIban
    // if the transaction is made toIban then it's get credited to toIban
    // need to parse the fromIban to see from where the amount gets deducted and parse toIban -> where the amount gets added
    // TODO -Change Client Http from Rest Template - PROBLEM SOLVED !!!
    // eroar Failed to complete request: org.springframework.web.client.ResourceAccessException: I/O error on PATCH request for "http://localhost:8200/account-current/debit/CURR-984021567114147515": Invalid HTTP method: PATCH; nested exception is java.net.ProtocolException: Invalid HTTP method: PATCH .... SI PROBLEMA ESTE This is due to HttpURLConnection only allowing the following HTTP methods: /* valid HTTP methods */
    // private static final String[] methods = {
    //    "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"
    @PatchMapping(path = "/account-current/credit/{iban}")
    public ResponseEntity<AccountCurrentDTO> creditedAccountCurrent(@PathVariable("iban") String iban, @RequestBody CreditAccountCurrentDTO amount){

        Optional<AccountCurrentDTO> accountCurrentDTO = accountCurrentService.getByIban(iban);


        IndividualDTO individualDTOById = individualRestClient.getIndividualById(accountCurrentDTO.get().getIndividualId());

        String fromIban = accountCurrentDTO.get().getIban();
        //credit the value from AccountCurrent with the value from transaction -> go to debit
        AccountCurrentDTO creditBalanceAccount = accountCurrentService.creditBalanceAccount(iban, amount.getAmount());
        creditBalanceAccount.setIndividual(individualDTOById);

        return ResponseEntity.ok(creditBalanceAccount);
    }

    //Payment from account                                                                           // change requestBody AmountDTO ???
    @PatchMapping(path = "/account-current/debit/{iban}")
    public ResponseEntity<AccountCurrentDTO> debitedAccountCurrent(@PathVariable("iban") String iban, @RequestBody DebitAccountCurrentDTO amount){

        Optional<AccountCurrentDTO> accountCurrentDTO = accountCurrentService.getByIban(iban);
        IndividualDTO individualById = individualRestClient.getIndividualById(accountCurrentDTO.get().getIndividualId());

        //debit the value from AccountCurrent with the value from transaction                     // amount from AmountDTO
        AccountCurrentDTO debitAccountCurrentDTO = accountCurrentService.debitBalanceAccount(iban, amount.getAmount());
        debitAccountCurrentDTO.setIndividual(individualById);

        return ResponseEntity.ok(debitAccountCurrentDTO);
    }

    @GetMapping(value = "/account-current/{individualId}")
    public ResponseEntity<List<AccountCurrentDTO>> retrieveAccountIndividual(@PathVariable("individualId") int individualId){

        List<AccountCurrentDTO> accountCurrentServiceByIndividual = accountCurrentService.getByIndividualId(individualId);

        if (accountCurrentServiceByIndividual.isEmpty()){

            return ResponseEntity.notFound().build();
        }
        IndividualDTO individualDTO = individualRestClient.getIndividualById(individualId);
        for (AccountCurrentDTO accountCurrent: accountCurrentServiceByIndividual) {
            accountCurrent.setIndividual(individualDTO);
        }
        return ResponseEntity.ok(accountCurrentServiceByIndividual);
    }

    @PostMapping(value = "/create-account/individuals/{individualId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE )
    public ResponseEntity<AccountCurrentDTO> createAccountCurrent(@PathVariable("individualId") int individualId){

        AccountCurrentDTO individualAccount = accountCurrentService.createIndividualAccount(individualId);
        IndividualDTO individualDTOById = individualRestClient.getIndividualById(individualId);

        individualAccount.setIndividual(individualDTOById);

        return ResponseEntity.ok(individualAccount);
    }

    @DeleteMapping(value = "delete/account-current/{iban}")
    public void deleteAccountFromRepository(@PathVariable("iban") String iban){

        accountCurrentService.deleteAccountByIban(iban);
    }
}
