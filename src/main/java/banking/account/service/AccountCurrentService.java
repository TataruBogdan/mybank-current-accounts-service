package banking.account.service;


import banking.account.dto.AccountCurrentDTO;

import java.util.List;
import java.util.Optional;

public interface AccountCurrentService {


    List<AccountCurrentDTO> getAll();
    Optional<AccountCurrentDTO> getByIban(String iban);
    List<AccountCurrentDTO> getByIndividualId(int individualId);
    AccountCurrentDTO updateBalanceAccount(String iban, Double balance);
    void deleteById(String id);
    AccountCurrentDTO createIndividualAccount(int individualId);

}
