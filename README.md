# 카카오페이 뿌리기 기능

### 핵심 기능 
1. 뿌리기 만들기
2. 받기
3. 조회 

### 문제 해결 
1. 헤더 요청 정보 
HTTP Header X-USER-ID X-ROOM-ID
filter 

2.  다수의 인스턴스로 동작 
@Transactional 사용

3. 대용량 트래픽 
레디스 캐시 사용 



