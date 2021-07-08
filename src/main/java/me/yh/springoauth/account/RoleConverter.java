package me.yh.springoauth.account;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.NoSuchElementException;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getKey();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        for (Role role : Role.values()) {
            if (role.getKey().equals(dbData)) {
                return role;
            }
        }
        throw new NoSuchElementException();
    }
}
