package me.yh.springoauth.account;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
public class AccountId implements Serializable {

    private String id;
    private String platformType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return Objects.equals(id, accountId.id) && Objects.equals(platformType, accountId.platformType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, platformType);
    }
}
