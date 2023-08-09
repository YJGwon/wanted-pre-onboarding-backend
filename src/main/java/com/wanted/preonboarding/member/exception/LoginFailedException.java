package com.wanted.preonboarding.member.exception;

import com.wanted.preonboarding.common.exception.ClientException;

public class LoginFailedException extends ClientException {

    private static final String TITLE = "로그인에 실패하였습니다.";
    private static final String MESSAGE = "아이디 또는 비밀번호를 확인하세요.";

    public LoginFailedException() {
        super(TITLE, MESSAGE);
    }
}
