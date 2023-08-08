package com.wanted.preonboarding.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 정보를 저장한다.")
    @Test
    void save() {
        // given
        final Member member = Member.ofNew("test@test.com", "test1234");

        // when
        final Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember.getId()).isNotNull();
    }
}
