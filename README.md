# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

이름 : 권예진

## API 명세

[swagger-ui](http://3.36.77.210:8080/swagger-ui/index.html)

## DB 테이블 구조

![wanted-pre-erd](https://github.com/YJGwon/wanted-pre-onboarding-backend/assets/89305335/ae5f1ecf-b2c1-4129-a555-e756aa9c481b)
## 배포 환경

![image](https://github.com/YJGwon/wanted-pre-onboarding-backend/assets/89305335/1e3e2954-383d-4a0d-812e-d9d0133799c5)
주소: http://3.36.77.210:8080

## 실행 방법

### 원격 endpoint 호출

[swagger-ui](http://3.36.77.210:8080/swagger-ui/index.html)

### local 환경 실행

#### 1. 소스 코드 clone

```shell
git clone -b main --single-branch https://github.com/YJGwon/wanted-pre-onboarding-backend.git
```

#### 2. gradle build

```shell
cd wanted-pre-onboarding-backend
chmod 755 gradlew
./gradlew clean build
```

#### 3. jar 파일 실행

```shell
cd build/libs
nohup java -jar pre-onboarding-0.0.1-SNAPSHOT.jar 1>{로그파일명} 2>&1 &
```

#### 4. 엔드포인트 호출

서버 실행 된 상태에서 `localhost:8080/swagger-ui/index.html`

## 데모 영상

- [정상 동작 flow](https://drive.google.com/file/d/1TALotoLUxl5XH76cuxsYpZe6M85O6m8Z/view?usp=drive_link)
  - 회원가입 -> 로그인 -> 게시글 생성 -> 목록, 특정 조회 -> 수정 -> 삭제
- [회원가입 예외처리](https://drive.google.com/file/d/1mQmQdeb_r0rKQDOhQj9ZPbQTXA903z0p/view?usp=drive_link)
  - 아이디, 비밀번호 입력값 예외 처리
- [로그인 예외처리](https://drive.google.com/file/d/1_rSoG3mX8qUDNOB4-YGNYfYqotEZfg6a/view?usp=drive_link)
  - 아이디, 비밀번호 입력값 예외 처리
  - 잘못된 아이디 또는 비밀번호 로그인 실패 처리
- [목록 조회 pagination](https://drive.google.com/file/d/10r-Z1_Bpr5Vp9-dLGiL1sTrHJrPR0Qh1/view?usp=drive_link)
  - 총 6개 게시물, 4 size 페이지 두 개로 나누어 조회
- [수정, 삭제 예외처리](https://drive.google.com/file/d/1QlB1QANpkqHgnsPv_0NYAAqweYuLWShY/view?usp=drive_link)
  - 작성자가 아닌 사용자가 수정, 삭제할 때 예외 처리

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

