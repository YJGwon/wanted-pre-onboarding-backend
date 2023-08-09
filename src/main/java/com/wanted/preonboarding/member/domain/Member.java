package com.wanted.preonboarding.member.domain;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.wanted.preonboarding.member.exception.LoginFailedException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private Email email;
    private Password password;

    public static Member ofNew(final String email, final String password) {
        return new Member(null, new Email(email), Password.fromPlainText(password));
    }

    public void checkPassword(final String password) {
        if (!this.password.isSamePassword(password)) {
            throw new LoginFailedException();
        }
    }
}
