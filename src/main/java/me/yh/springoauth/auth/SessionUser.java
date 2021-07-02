package me.yh.springoauth.auth;

import lombok.Getter;
import me.yh.springoauth.entity.Account;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private final String name;
    private final String email;
    private final String picture;

    public SessionUser(Account a) {
        this.name = a.getNickname();
        this.email = a.getEmail();
        this.picture = a.getProfileImage();
    }
}