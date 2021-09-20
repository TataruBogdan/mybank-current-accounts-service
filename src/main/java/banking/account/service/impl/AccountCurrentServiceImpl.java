package banking.account.service.impl;


import banking.account.dao.AccountRepository;
import banking.account.dto.AccountCurrentDTO;
import banking.account.model.AccountCurrent;
import banking.account.service.AccountCurrentMapper;
import banking.account.service.AccountCurrentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static banking.account.model.CurrentStatus.ACTIVE;


@RequiredArgsConstructor
@Service
public class AccountCurrentServiceImpl implements AccountCurrentService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final AccountCurrentMapper accountCurrentMapper;



    @Override
    public List<AccountCurrentDTO> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountCurrent -> accountCurrentMapper.accountToDTO(accountCurrent))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountCurrentDTO> getByIban(String iban) {
        return accountRepository.findById(iban)
                .map(accountCurrent -> accountCurrentMapper.accountToDTO(accountCurrent));
    }

    @Override
    public void deleteById(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountCurrentDTO createIndividualAccount(int individualId) {

        AccountCurrent accountCurrent = new AccountCurrent();
        accountCurrent.setIban(UUID.randomUUID().toString());
        accountCurrent.setBalance(0.0);
        accountCurrent.setIndividualId(individualId);
        accountCurrent.setStartDate(new Date());
        accountCurrent.setCurrentStatus(ACTIVE);
        accountCurrent.setPrimaryAccount(true);
        AccountCurrent savedAccountCurrent = accountRepository.save(accountCurrent);
        AccountCurrentDTO savedAccountCurrentDTO = accountCurrentMapper.accountToDTO(savedAccountCurrent);

        return savedAccountCurrentDTO;
    }






}
