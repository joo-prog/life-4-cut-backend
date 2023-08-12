package com.onebyte.life4cut.common.vo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ToString
@EqualsAndHashCode
public class Email {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);


    private String value;

    private Email() {
    }

    protected Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        if (!isValidEmail(value)) {
            throw new IllegalArgumentException("Invalid Email " + value);
        }

        return new Email(value);
    }

    public String getValue() {
        return value;
    }

    private static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
