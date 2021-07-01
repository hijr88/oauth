package me.yh.springoauth;

import lombok.RequiredArgsConstructor;
import me.yh.springoauth.account.repository.AccountRepository;
import me.yh.springoauth.entity.Account;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {

    private final AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) {
        Optional<Account> woo = accountRepository.findAccountById("woo");
        woo.ifPresent(System.out::println);
    }
}
