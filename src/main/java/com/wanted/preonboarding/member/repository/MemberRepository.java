package com.wanted.preonboarding.member.repository;

import com.wanted.preonboarding.member.domain.Member;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);
}
