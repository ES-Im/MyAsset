# Mockoon으로 외부 fake 금융 api 설계

## Mockoon으로 외부 뱅킹 API "흉내"
금융결제원 흐름에서 핵심은 아래 2가지가 고정적으로 요구됨
> 1. 유저 인증 후 외부 API가 access_token을 응답
> 2. 데이터 요청 시 access_token은 Header(Authorization)에 포함, 사용자 식별자는 query/path(필요 시 body)에 포함

- Refresh 및 실제 인증정보 데이터셋 구성까지 구현하려면 별도 외부 금융 모듈 수준이 필요하므로, 현 프로젝트에서는 Mockoon을 이용하여 아래 수준으로 “흉내”만 낸다.

#### 인증 관련

- Fake API Server(Mockoon)
    - POST /api/auth/token
```json
{
  "access_token": "mock-access-token",
  "token_type": "Bearer",
  "expires_in": 315360000
}
```
- Backend Server
    - 토큰 만료/refresh는 생략(무한 만료로 가정)
    - 발급 받은 access_token은 Redis에 저장: userKey -> mock-access-token
    - 유저 식별 정보(fintech_user_num, external_user_id, providerCode)는 “외부에서 내려왔다고 가정”하고 DB에 저장
    -
#### 정보 불러오기 관련

- Fake API Server(Mockoon)
    - 모든 요청은 Authorization: Bearer mock-access-token 헤더 존재 여부만 검사(없으면 401)

- Backend 호출 정책
    - 계좌 조회는 “연동하기” 시점에 1회 즉시 호출
    - 거래내역 조회는 스케줄러/cron으로 1시간마다 호출(증분 구간만 조회)

#### 관련 의존방향 : MyDataApi(inbound) > MyDataAdapter(adaptor) > MyDataPort(application)
    // 1 accessToken 정보 받아오기 (fake token)
    /*
     * /api/mydata/auth/token
     */

    // 2 계좌 / 카드 정보 받아오기
    /*
     * 계좌 : /api/mydata/accountinfo/list
     * 카드 : /api/mydata/cards
     */


<hr>

# API 정리
| 구분     | HTTP URL               | Request Body        |Response Body |비고|
|--------|------------------------|---------------------|---|---|
| 계좌통합조회 | POST /accountinfo/list | 고객식별정보 |||
| 카드목록조회 | GET /cards | 고객식별정보 |||


    { “card_id": "abcABC123abcABC123abcABC",
    "card_num_masked": "123456******3456",
    "card_name": "카드상품명",
    "card_member_type": "1" },