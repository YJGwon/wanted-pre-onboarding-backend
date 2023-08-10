# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

이름 : 권예진

## 실행 방법

## DB 테이블 구조

## 데모 영상

## 구현 방법

#### 도메인 규칙 검증

- 최대한 domain level에서 검증, 단순 입력값 검증은 controller단에서도 추가 수행
  - 단위 테스트 용이 + 가벼운 service 로직
  - 유효하지 않은 request를 빠르게 거부하면서도 도메인 신뢰성 유지

#### 예외 처리

- 비즈니스 규칙 상 정의되었으며 의도된 예외를 구별하기 위해 custom exception 정의
- Response status에 따라 상속 계층 구성
  - 각 도메인 영역에서는 각 exception을 상속받아 필요한 exception 정의하기만 하면 `ControllerAdvice`에서 알맞는 http status 응답
- 에러 응답 시 `ErrorResponse` 사용
  - RFC 에러 응답 spec 준수

#### 비밀번호 암호화

- 복호화 할 수 없도록 단방향 암호화(SHA-256 사용)
  - 같은 평문에 대해 같은 결과가 나오므로 로그인 시 비교 가능

#### 토큰 발급

- `회원 식별자(id)를 subject로 가지는 token 발급`이라는 명세는 domain에서 interface로 정의하고, jwt 구현체는 해당 interface 상속
  - token 발급 방식을 추상화하여 domain에서는 어떤 값으로 인증, 인가를 처리할 지에 대해서만 정의

#### 사용자 인증

- `HandlerInterceptor` 구현체 `AuthenticationInterceptor`에서 token 검증
  - Controller 또는 handler method에 Annotation(`@Authentication`) 달아서 적용할 수 있도록 구현: code level에서 요청 권한 알아보기 쉬움
- Annotation 검사 시 `getClass`아닌 `getBeanType`사용
  - `getBeanType`사용하면 proxy 객체일 경우에도 target instance의 type을 반환
  - `getClass`사용하면 Controller 객체에 AOP 적용된 경우 class level의 annotation 가져오지 못함

## API 명세

