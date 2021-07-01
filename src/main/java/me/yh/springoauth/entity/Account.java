package me.yh.springoauth.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@ToString(callSuper = true)

@EntityListeners(AuditingEntityListener.class)
@DynamicInsert                      //null 값은 insert 할 때 제외
@DynamicUpdate
@Entity
public class Account extends Address implements Persistable<String > {

    @Id
    @Column(length = 50, updatable = false)
    private String id;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(length = 300, nullable = false, unique = true, updatable = false)
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @Column(length = 500)
    @ColumnDefault("'none'")
    private String profileImage;

    @Column(length = 1, nullable = false)
    @ColumnDefault("1")
    private Boolean enable = true;

    @ColumnDefault("'ROLE_USER'")
    private String role = "ROLE_USER";

    @Override
    public boolean isNew() {
        return createDate == null;
    }

    public Account(String id, String password, String nickname, String email) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public static Account testUser(String id) {
        Account Account = new Account();
        Account.id = id;
        Account.password = "pass";
        Account.nickname = "테스트";
        Account.email = "test@gmail";
        Account.profileImage = "none";
        return Account;
    }

    public static Account testUser(String id, String role) {
        Account Account = new Account();
        Account.id = id;
        Account.password = "pass";
        Account.nickname = "테스트";
        Account.email = "test@gmail";
        Account.profileImage = "none";
        Account.role = role;
        return Account;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }*/

    public void changeProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void disable() {
        this.enable = false;
    }
}

