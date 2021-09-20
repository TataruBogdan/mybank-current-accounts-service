package banking.account.service;


import banking.account.dto.AccountCurrentDTO;

import java.util.List;
import java.util.Optional;

public interface AccountCurrentService {


    List<AccountCurrentDTO> getAll();
    Optional<AccountCurrentDTO> getByIban(String iban);
    void deleteById(String id);
    //void saveIndividual(int individualId, String iban);
    AccountCurrentDTO createIndividualAccount(int individualId);

}
