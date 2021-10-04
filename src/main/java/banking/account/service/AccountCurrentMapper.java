package banking.account.service;


import banking.commons.dto.AccountCurrentDTO;
import banking.account.model.AccountCurrent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountCurrentMapper {

    AccountCurrentDTO accountToDTO(AccountCurrent accountCurrent);

    List<AccountCurrentDTO> toAccountCurrentDTO(List<AccountCurrent> accountCurrentList);

    AccountCurrent toAccountCurrent(AccountCurrentDTO accountCurrentDTO);

}
