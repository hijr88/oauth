package me.yh.springoauth;

import lombok.RequiredArgsConstructor;
import me.yh.springoauth.account.Account;
import me.yh.springoauth.account.AccountId;
import me.yh.springoauth.account.repository.AccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {

    private final AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) {
        Optional<Account> woo = accountRepository.findById(new AccountId("woo", "this"));
        Account account = woo.orElseGet(() -> accountRepository.save(new Account("woo", "1234", "요!", "woo@gmail", "none", "this")));
        System.out.println(account);
    }
}