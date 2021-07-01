package me.yh.springoauth.account.repository;

import me.yh.springoauth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String>, AccountRepositoryCustom {

    Optional<Account> findAccountById(String id);

}
