package me.yh.springoauth.account;

import lombok.*;
import me.yh.springoauth.abstractentity.Address;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@ToString(callSuper = true)

@EntityListeners(AuditingEntityListener.class)
@DynamicInsert                      //null 값은 insert 할 때 제외
@DynamicUpdate
@IdClass(AccountId.class)
@Entity
public class Account extends Address implements Persistable<String > {

    @Id
    @Column(length = 100, updatable = false)
    private String id;

    @Id
    @Column(length = 30, updatable = false)
    private String platformType;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(length = 300, unique = true)
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

    @Convert(converter = RoleConverter.class)
    private Role role = Role.USER;

    @Override
    public boolean isNew() {
        return createDate == null;
    }

    public Account(String id, String password, String nickname, String email, String profileImage, String platformType) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.platformType = platformType;
    }

    public static Account testUser(String id) {
        Account a = new Account();
        a.id = id;
        a.password = "pass";
        a.nickname = "테스트";
        a.email = "test@gmail";
        a.profileImage = "none";
        a.platformType = "this";
        return a;
    }

    public static Account testUser(String id, Role role) {
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

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public Account changeProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        return this;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void disable() {
        this.enable = false;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void updateRole(Role role) {
        this.role = role;
    }
}

