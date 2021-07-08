package me.yh.springoauth.account.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.yh.springoauth.account.Account;

import javax.persistence.EntityManager;
import java.util.Optional;

import static me.yh.springoauth.account.QAccount.account;

public class AccountRepositoryImpl implements AccountRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public AccountRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Account> findAccount(String id, String nickname) {
        Account a = queryFactory
                .select(account)
                .from(account)
                .where(idEq(id), nicknameEq(nickname))
                .fetchOne();

        return Optional.ofNullable(a);
    }


    private BooleanExpression idEq(String id) {
        if (id == null) return null;
        return account.id.eq(id);
    }

    private BooleanExpression nicknameEq(String nickname) {
        if (nickname == null) return null;
        return account.nickname.eq(nickname);
    }
}
