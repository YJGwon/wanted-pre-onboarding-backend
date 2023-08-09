package com.wanted.preonboarding.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.member.domain.Email;
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

    @DisplayName("이메일로 회원 정보를 조회한다.")
    @Test
    void findByEmail() {
        // given
        final String rawEmail = "test@test.com";
        final Member expected = Member.ofNew(rawEmail, "test1234");
        memberRepository.save(expected);

        // when
        final Email email = new Email(rawEmail);
        final Member found = memberRepository.findByEmail(email).get();

        // then
        assertThat(found.getId()).isEqualTo(expected.getId());
    }
}
