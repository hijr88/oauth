package me.yh.springoauth.account.dto;

import lombok.Data;

@Data
public class AccountSimple {

    private String id;
    private String password;
    private String nickname;
    private String platformType;
}
