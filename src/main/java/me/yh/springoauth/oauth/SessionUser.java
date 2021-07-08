package me.yh.springoauth.oauth;

import lombok.Getter;
import me.yh.springoauth.account.Account;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private final String nickname;
    private final String email;
    private final String picture;
    private final String role;
    private final String platformType;

    public SessionUser(Account a) {
        this.nickname = a.getNickname();
        this.email = a.getEmail();
        this.picture = a.getProfileImage();
        this.role = a.getRoleKey();
        this.platformType = a.getPlatformType();
    }
}