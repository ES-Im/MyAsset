package dev.es.myasset.domain.shared;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Email(
        String email
) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public Email {
        if(!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
    }
}
