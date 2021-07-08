package me.yh.springoauth.account.repository;

import me.yh.springoauth.account.Account;
import me.yh.springoauth.account.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, AccountId>, AccountRepositoryCustom {

    Optional<Account> findAccountById(String id);

    Optional<Account> findAccountByIdAndPlatformType(String id, String platformType);
}
