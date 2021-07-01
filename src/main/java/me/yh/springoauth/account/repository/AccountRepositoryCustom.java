package me.yh.springoauth.account.repository;

import me.yh.springoauth.entity.Account;

import java.util.Optional;

public interface AccountRepositoryCustom {

    Optional<Account> findAccount(String id, String nickname);
}
