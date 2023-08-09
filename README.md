# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

이름 : 권예진

## 실행 방법

## DB 테이블 구조

## 데모 영상

## 구현 방법

### 사용자 회원가입 엔드포인트

- 비밀번호 암호화: 복호화 할 수 없도록 단방향 암호화(SHA-256 사용)
- 입력값 검증: VO 사용하여 도메인 level 검증 + DTO에 Bean Validation 적용하여 Controller level에서도 검증
  - 유효하지 않은 request를 빠르게 거부하면서도 도메인 신뢰성 유지

### 사용자 로그인 엔드포인트

- Token 발급: `회원 식별자(id)를 subject로 가지는 token 발급`이라는 명세는 domain에서 interface로 정의하고, jwt 구현체는 해당 interface 상속
  - token 발급 방식을 추상화하여 domain에서는 어떤 값으로 인증, 인가를 처리할 지에 대해서만 관리

## API 명세

