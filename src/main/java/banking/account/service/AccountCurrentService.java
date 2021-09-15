package banking.account.service;


import banking.account.dto.AccountCurrentDTO;

import java.util.List;
import java.util.Optional;

public interface AccountCurrentService {


    List<AccountCurrentDTO> getAll();
    Optional<AccountCurrentDTO> getById(String id);
    void deleteById(String id);
    void save (AccountCurrentDTO accountCurrentDTO);




}
