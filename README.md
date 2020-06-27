## 카카오페이 뿌리기 기능


### 개발 환경
#### 기본 환경
- Java8
- Spring Boot 2.3.1
- Lombok plugin

### 핵심 기능
#### 요구사항
- 뿌리기, 받기, 조회 기능을 수행하는 REST API 를 구현
- 사용자의 식별값은 숫자 형태이며 "X-USER-ID" 라는 HTTP Header로 전달
- 대화방의 식별값은 문자 형태이며 "X-ROOM-ID" 라는 HTTP Header로 전달
- 잔액에 관련된 체크 진행 없음 
- 다수의 서버와 다수의 인스턴스로 동작하더라도 기능에 문제가 없도록 설계
- 기능 및 제약사항에 대한 단위테스트 작성 

#### 폴더 구조 
```
songiui-MacBookPro:kakaopaycorp songi$ tree api -L 4  -C
api
├── ApiApplication.java
├── domain
│   └── event
│       ├── api
│       │   └── RandomPushController.java
│       ├── dto
│       │   ├── ApiResultDto.java
│       │   └── RandomPushRequestDto.java
│       ├── model
│       │   ├── RandomPush.java
│       │   └── RandomPushDetail.java
│       ├── repository
│       │   ├── RandomPushDetailRepository.java
│       │   └── RandomPushRepository.java
│       ├── service
│       │   ├── RandomPushService.java
│       │   └── TokenKeygen.java
│       └── vo
└── global
    ├── cache
    │   ├── RedisPrefix.java
    │   └── annotation
    │       ├── RedisCacheEvict.java
    │       └── RedisCacheable.java
    ├── web
    │   └── filter
    │       ├── ReadableRequestInfoWrapper.java
    │       └── RequestInfoFilter.java
    └── webConfig.java
```
#### API 정보 
1. 뿌리기 API 
- 기능 
   - 뿌리기 만들기
   - 토큰 발급 
     - 세자리의 예측불가 문자열
     - Md5
   
- Request 정보<BR>
url : GET /event/random-push/price

|파라미터명|타입|비고|
|------|---|---|
|totalPushPrice|int|뿌릴 금액|
|userCount|int|뿌릴 인원|


2. 받기 API 
- 기능 
   - 토큰만료체크
   - 뿌리기 벨리데이션
   - 뿌리기 발급 

- Request 정보<BR>
url : POST /event/random-push/publish

|파라미터명|타입|비고|
|------|---|---|
|token|int|String|
   
3. 조회 API 
   - 발급현황 조회 
   
- Request 정보<BR>
url : GET /event/random-push/status

### 도메인 모델 클래스 다이어그램
<img width="682" alt="스크린샷 2020-06-27 오후 12 17 47" src="https://user-images.githubusercontent.com/22117193/85913476-3f88cf80-b870-11ea-9028-148ab35aac3f.png">

### 문제 해결 

1. 다수의 서버 / 다수의 인스턴스로 동작

   - 트랜잭션 처리 

2. 대용량 트래픽 대비

   - Redis Cache 사용
   - @RedisCacheable, @RedisCacheEvict 등 custom annotaion 정의 
