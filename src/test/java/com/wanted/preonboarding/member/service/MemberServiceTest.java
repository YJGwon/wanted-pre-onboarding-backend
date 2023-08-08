package com.wanted.preonboarding.member.service;

import static org.assertj.core.api.Assertions.assertThatNoException;

import com.wanted.preonboarding.common.testbase.ServiceTestBase;
import com.wanted.preonboarding.member.dto.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberServiceTest extends ServiceTestBase {

    @Autowired
    private MemberService memberService;

    @DisplayName("회원 정보를 생성하여 저장한다.")
    @Test
    void create() {
        // given
        final MemberRequest request = new MemberRequest("test@test.com", "test1234");

        // when & then
        assertThatNoException()
                .isThrownBy(() -> memberService.create(request));
    }
}
