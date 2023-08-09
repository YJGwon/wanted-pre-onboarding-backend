package com.wanted.preonboarding.member.service;

import com.wanted.preonboarding.member.domain.Email;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.domain.TokenProvider;
import com.wanted.preonboarding.member.dto.LoginRequest;
import com.wanted.preonboarding.member.dto.MemberRequest;
import com.wanted.preonboarding.member.exception.LoginFailedException;
import com.wanted.preonboarding.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(final MemberRequest request) {
        final Member member = Member.ofNew(request.email(), request.password());
        memberRepository.save(member);
    }

    public String login(final LoginRequest request) {
        final Email email = new Email(request.email());

        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(LoginFailedException::new);
        member.checkPassword(request.password());

        return tokenProvider.create(member.getId().toString());
    }
}
