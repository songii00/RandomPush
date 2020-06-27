## 카카오페이 뿌리기 기능

### 핵심 기능 

1. 뿌리기 API 
   - 뿌리기 만들기
   - 토큰 발급 
     
     - 세자리의 예측불가 문자열
     
     - Md5
   
2. 받기 API 
   - 토큰만료체크 
   - 뿌리기 벨리데이션 
   - 뿌리기 발급 
   
3. 조회 API 
   - 발급현황 조회 

### 도메인 모델 클래스 다이어그램
<img width="682" alt="스크린샷 2020-06-27 오후 12 17 47" src="https://user-images.githubusercontent.com/22117193/85913476-3f88cf80-b870-11ea-9028-148ab35aac3f.png">

### 문제 해결 

1. 다수의 서버 / 다수의 인스턴스로 동작

   - 트랜잭션 처리 

2. 대용량 트래픽 대비

   - Redis Cache 사용
   - @RedisCacheable, @RedisCacheEvict 등 custom annotaion 정의 
