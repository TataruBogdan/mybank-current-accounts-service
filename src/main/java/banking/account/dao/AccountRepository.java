package banking.account.dao;


import banking.account.model.AccountCurrent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountCurrent, String> {
}
