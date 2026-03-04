# Mockoon으로 외부 fake 금융 api 설계

## Mockoon으로 외부 뱅킹 API "흉내"
### 금융결제원 흐름에서 핵심은 아래 2가지가 고정적으로 요구됨
> 1. 유저 인증 후 외부 API가 access_token을 응답
> 2. 데이터 요청 시 access_token은 Header(Authorization)에 포함, 사용자 식별자는 query/path(필요 시 body)에 포함

- Refresh 및 실제 인증정보 데이터셋 구성까지 구현하려면 별도 외부 금융 모듈 수준이 필요하므로, 현 프로젝트에서는 Mockoon을 이용하여 아래 수준으로 “흉내”만 낸다.
### Faker 라이브러리를 이용
 - 외부 뱅킹 API 응답은 식별을 위한 핵심 키 값만 고정하고, 그 외 필드는 요청마다 랜덤 값으로 생성된다.
 - 즉, 같은 API를 여러 번 호출하면 다음 항목들은 매 호출마다 값이 달라질 수 있다.
   - 거래내역의 금액/시간/메모(printed_content)/거래유형(tran_type) 등
   - 카드 청구내역의 결제금액(paid_amt)/결제시각(paid_time)/가맹점명(merchant_name) 등
   - 계좌통합조회 결과의 상품명(product_name), 잔액(balance_amt) 등 (단, 프로젝트 목적상 잔액을 고정하고 싶다면 별도 고정값으로 처리 가능)

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

---

# API 정리 요약
 - JSON 명세는 금결원 오픈 API를 참고하되 단순화한다.
 - 인증: Authorization: Bearer <accessToken>
 - 모든 응답은 공통으로 code, message 를 가진다.
 - 자세한 api 스펙은 "mockoon_api_detail.md"에 정리

| 구분        | Method | URL                                            | Request                                                                                                                           | Response                                                                                                                                                |
| --------- | ------ | ---------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 사용자 인증    | POST   | `/api/mydata/token`                            | **Body**: `user_key`                                                                                                              | `code`, `message`, `token_type`, `access_token`, `user_seq_no`, `bank[] (fintech_use_num)`                                                              |
| 계좌통합조회    | POST   | `/api/mydata/accountinfo/list`                 | **Header**: `Authorization: Bearer <accessToken>`<br>**Body**: `fintech_use_num`                                                  | `code`, `message`, `accounts[]` (예: `bank_code_std`, `account_num`, `balance_amt`, `product_name`, `account_type`)                                      |
| 계좌 거래내역조회 | GET    | `/api/mydata/account/transaction_list/fin_num` | **Header**: `Authorization: Bearer <accessToken>`<br>**Query**: `fintech_use_num`, `from_date(YYYY-MM-DD)`, `to_date(YYYY-MM-DD)` | `code`, `message`, `api_tran_id`, `res_list[]` (예: `tran_date`, `tran_time`, `inout_type`, `tran_type`, `printed_content`, `tran_amt`)                  |
| 카드목록조회    | GET    | `/api/mydata/cards`                            | **Header**: `Authorization: Bearer <accessToken>`<br>**Query**: `user_seq_no`, `bank_code_std`                                    | `code`, `message`, `card_list[]` (예: `card_id`, `card_num_masked`, `card_name`)                                                                         |
| 카드기본정보조회  | GET    | `/cards/issue_info`                            | **Header**: `Authorization: Bearer <accessToken>`<br>**Query**: `card_id`, `bank_code_std`                                        | `code`, `message`, `card_type`, `settlement_bank_code`, `settlement_account_num`                                                                        |
| 카드청구상세조회  | GET    | `/api/mydata/cards/bills/detail`               | **Header**: `Authorization: Bearer <accessToken>`<br>**Query**: `user_seq_no`, `bank_code_std`, `charge_month(YYYYMM)`            | `code`, `message`, `api_tran_id`, `bill_list[]` (예: `card_id`, `paid_date`, `paid_time`, `paid_amt`, `settlement_day`, `merchant_name`, `product_type`) |

---
