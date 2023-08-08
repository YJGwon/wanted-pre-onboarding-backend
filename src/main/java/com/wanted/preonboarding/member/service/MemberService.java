package com.wanted.preonboarding.member.service;

import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.dto.MemberRequest;
import com.wanted.preonboarding.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void create(final MemberRequest request) {
        final Member member = Member.ofNew(request.email(), request.password());
        memberRepository.save(member);
    }
}
