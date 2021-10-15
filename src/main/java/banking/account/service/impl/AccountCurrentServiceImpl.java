package banking.account.service.impl;

import banking.account.dao.AccountRepository;
import banking.account.model.AccountCurrent;
import banking.account.service.AccountCurrentMapper;
import banking.account.service.AccountCurrentService;
import banking.commons.dto.AccountCurrentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static banking.commons.dto.idGen.IdGenerator.idGen;
import static banking.commons.dto.types.CurrentStatus.ACTIVE;


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
    public List<AccountCurrentDTO> getByIndividualId(int individualId){

        List<AccountCurrent> accountRepositoryByIndividual = accountRepository.findByIndividualId(individualId);
        List<AccountCurrentDTO> accountCurrentDTO = accountCurrentMapper.toAccountCurrentDTO(accountRepositoryByIndividual);

        return accountCurrentDTO;

    }

    @Override
    public AccountCurrentDTO updateBalanceAccount(String iban, Double balance){


        AccountCurrent accountCurrent = accountRepository.getById(iban);
        accountCurrent.setBalance(balance);
        AccountCurrent savedAccountCurrent = accountRepository.save(accountCurrent);

        return accountCurrentMapper.accountToDTO(savedAccountCurrent);

    }

    @Override
    public AccountCurrentDTO creditBalanceAccount(String iban, Double balance) {

        AccountCurrent accountCurrent = accountRepository.getById(iban);
        Double currentBalance = accountCurrent.getBalance();
        accountCurrent.setBalance(balance + currentBalance);
        AccountCurrent saveAccountCurrent = accountRepository.save(accountCurrent);

        return accountCurrentMapper.accountToDTO(saveAccountCurrent);
    }

    @Override
    public AccountCurrentDTO debitBalanceAccount(String iban, Double balance) {

        AccountCurrent accountCurrent = accountRepository.getById(iban);
        Double currentBalance = accountCurrent.getBalance();
        accountCurrent.setBalance(currentBalance - balance);
        AccountCurrent savedAccountCurrent = accountRepository.save(accountCurrent);

        return accountCurrentMapper.accountToDTO(savedAccountCurrent);
    }

    @Override
    public AccountCurrentDTO createIndividualAccount(int individualId) {

        AccountCurrent accountCurrent = new AccountCurrent();
        accountCurrent.setIban(idGen("CURR"));
        accountCurrent.setBalance(0.0);
        accountCurrent.setIndividualId(individualId);
        accountCurrent.setStartDate(LocalDateTime.now());
        accountCurrent.setCurrentStatus(ACTIVE);
        accountCurrent.setPrimaryAccount(true);
        AccountCurrent savedAccountCurrent = accountRepository.save(accountCurrent);
        AccountCurrentDTO savedAccountCurrentDTO = accountCurrentMapper.accountToDTO(savedAccountCurrent);

        return savedAccountCurrentDTO;
    }


    @Override
    public void deleteAccountByIban(String iban) {
        accountRepository.deleteById(iban);
    }


}
