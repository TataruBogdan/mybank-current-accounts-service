package banking.account;

import banking.account.dao.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrentAccountApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(CurrentAccountApplication.class, args);
    }

    @Override
    public void run(String... args) {

        //TODO
        //Caused by: org.postgresql.util.PSQLException: ERROR: column "account_type" is of type typeaccount but expression is of type integer
        //Hint: You will need to rewrite or cast the expression.

//        logger.info("Create account current -> {}",
//                accountRepository.save(
//                        new AccountCurrent("ABC123",  100.00, 123, new Date(),
//                                CurrentStatus.ACTIVE , true, AccountType.CURRENT )));
//
//        logger.info("Account current -> {}", accountRepository.findById("ABC123"));
    }
}
