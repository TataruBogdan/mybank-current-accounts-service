package banking.account.service.impl;


import banking.account.dao.AccountRepository;
import banking.account.dto.AccountCurrentDTO;
import banking.account.service.AccountCurrentMapper;
import banking.account.service.AccountCurrentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<AccountCurrentDTO> getById(String id) {
        return accountRepository.findById(id)
                .map(accountCurrent -> accountCurrentMapper.accountToDTO(accountCurrent));
    }

    @Override
    public void deleteById(String id) {
        accountRepository.deleteById(id);
    }



}
