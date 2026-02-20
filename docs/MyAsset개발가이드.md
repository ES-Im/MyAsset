# MyAsset 개발가이드

## 아키텍처
- 헥사고날 아키텍처
- 도메인 모델 패턴

### 계층
- Domain Layer
- Application Layer
- Adapter Layer

> 의존방향 : Actor > adaptor > application > domain

## 패키지
- domain
- application
  - required
  - provided
- adapter
  - webapi
  - persistence
  - integration
  - security
