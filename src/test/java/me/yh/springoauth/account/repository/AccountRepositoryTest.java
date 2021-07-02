package me.yh.springoauth.account.repository;

import me.yh.springoauth.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void init() {
        Account account1 = Account.testUser("yoo");
        account1.setEmail("wise");
        accountRepository.save(account1);
    }

    @Test
    void findAccountById() {
        Optional<Account> account = accountRepository.findAccountById("yoo");

        assertTrue(account.isPresent());
        System.out.println(account.get());
    }

    @DisplayName("동적 쿼리")
    @Test
    void findAccount() {
        accountRepository.findAccount("yoo", "wer");
    }

    @Test
    void findByEmail() {
        Optional<Account> account = accountRepository.findByEmail("wise");

        assertTrue(account.isPresent());
        System.out.println(account.get());
    }

}