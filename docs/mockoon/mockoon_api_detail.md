// to-do : 엔티티에 markSynced 메서드를 bank, card, stock 엔티티에 전부박아넣을 것.

## 사용자 인증
- Oauth인증으로 SMS 본인인증을 임시적으로서버 내부 식별 키인 userKey 인증 대체한다. 즉, 외부 연동을 한다면 갈아엎을 부분
- [금결원 - OAuth인증](https://developers.kftc.or.kr/dev/openapi/open-banking/oauth)
### Request
- `POST /api/mydata/token`

- body
```json
{
  "user_key": "38dbcc6c-30ce-4669-9100-4f7408a9e58e"
}
```
#### Response (200)
```json
{
  "code": "A0000",
  "message": "Success",
  "token_type": "Bearer",
  "access_token": "access_token",
  "user_seq_no": "U242142305",
  "bank": [
    {
      "fintech_use_num": "931091949809763098176093"
    },
    {
      "fintech_use_num": "978437863743221050492705 "
    }
  ]
}
```

---
# BANK

## 계좌통합조회 
[금결원 - 계좌통합조회](https://developers.kftc.or.kr/dev/openapi/account-info/inquiry)
### HTTP
- `POST /api/mydata/accountinfo/list`

### Request
#### Headers
- `Authorization: Bearer <accessToken>`
- body
```json
{
  "fintech_use_num": "931091949809763098176093"
}
```

#### Response (200)
```json
{
  "code": "A0000",
  "message": "Success",
  "accounts": [
    {
      "bank_code_std": "011",
      "account_num": "0001234567890123",
      "balance_amt": "10000000",
      "product_name": "NH1934우대통장",
      "account_type": "1"
    }
  ]
}
```
- account_type : 1:수시입출금,  2:예적금  6:수익증권


---

## 계좌 - 거래내역 조회
[금결원 - 거래내역조회](https://developers.kftc.or.kr/dev/openapi/open-banking/transaction)
### HTTP
- `GET /api/mydata/account/transaction_list/fin_num?fintech_use_num=931091949809763098176093&from_date=2026-03-01&to_date=2026-03-31`
- query params
  - fintech_use_num : 계좌 식별 번호
  - from_date : 조회 시작 일자(YYYY-MM-DD)
  - to_date : 조회 종료 일자(YYYY-MM-DD)

### Request
#### Headers
- `Authorization: Bearer <accessToken>`

#### Response (200)
```json
{
  "code": "A0000",
  "message": "Success",
  "api_tran_id": "AAyIBxqErTXnNwzV451P",
  "res_list": [
    {
      "tran_date": "20260305",
      "tran_time": "091530",
      "inout_type": "입금",
      "tran_type": "급여",
      "printed_content": "급여/월급여",
      "tran_amt": "5000000"
    },
    {},
    ...
  ]
}
```
- inout_type : "입금", "출금"
- tran_type : 현금, 대체, 급여, 타행환, F/B출금 등

---

# CARD

## 카드 - 카드목록 조회
### HTTP
 - `GET /api/mydata/cards?user_seq_no=U242142305&bank_code_std=041`
 - query params
     - user_seq_no : 사용자일련번호
     - bank_code_std : 카드사 대표코드(금융기관 공동코드)

#### Headers
- `Authorization: Bearer <accessToken>`

#### Response(200)

```json
{
  "code": "A0000",
  "message": "Success",
  "card_list": [
    {
      "card_id": "YNsM7D8WwpgMsz1CBCWGmrH3",
      "card_num_masked": "662133******5491",
      "card_name": "신한 딥드림"
    }
  ]
}
```

---

## 제목
[금결원 - 카드기본정보조회](https://developers.kftc.or.kr/dev/openapi/open-banking/issue_info)
### GET
- `GET /cards/issue_info?card_id=YNsM7D8WwpgMsz1CBCWGmrH3&bank_code_std=041`
- query params
    - card_id : 카드 식별자
    - bank_code_std : 카드사 대표코드(금융기관 공동코드)
  
### Request
#### Headers
- `Authorization: Bearer <accessToken>`

#### Response (200)
```json
{
  "code": "A0000",
  "message": "Success",
  "card_type": "01",
  "settlement_bank_code": "011",
  "settlement_account_num": "0001234567890123"
}
```
* card_type : “01”은 신용, “02”는 체크(직불포함), “03”은 소액신용체크를 의미

---
## 카드 - 거래내역 조회
[금결원 - 카드청구기본정보조회](https://developers.kftc.or.kr/dev/openapi/open-banking/bills_detail)
### HTTP
- `GET /api/mydata/cards/bills/detail?user_seq_no=U242142305&bank_code_std=041&charge_month=202501`
- query params
  - user_seq_no : 사용자일련번호
  - bank_code_std : 카드사 대표코드(금융기관 공동코드)
  - charge_month : 청구년월(YYYYMM)

#### Headers
- `Authorization: Bearer <accessToken>`

#### Response (200)
```json

{
  "code": "A0000",
  "message": "Success",
  "api_tran_id": "AAChS6dboKwu0q3cVdRN",
  "bill_list": [
    {
      "card_id": "YNsM7D8WwpgMsz1CBCWGmrH3",
      "paid_date": "20250117",
      "paid_time": "024343",
      "paid_amt": "146000",
      "settlement_day": "01",
      "merchant_name": "스타벅스",
      "product_type": "01"
    },
    {},
    ...
  ]
}
```
* merchant_name_masked : 마스킹된 가맹점명
* product_type : //  “01”은 일시불, “02”는 신용판매할부, “03”은 현금서비스
* 할부회차에 대해서는 '금결원'에서 제공하지 않음 (- 외부 카드사 스펙이 더 필요.)

---