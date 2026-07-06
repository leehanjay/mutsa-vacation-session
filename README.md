# mutsa-vacation-session

멋사 방학세션 백엔드 과제

팀원: 이한재, 송승환

## 🛠 기술 스택

- Java 17
- Spring Boot 3.5.16
- Spring Data JPA
- MySQL 8.0
- Gradle - Groovy

## 🚀 Convention

우리 팀의 원활한 협업과 일관성 있는 코드 관리를 위한 규칙입니다. 모든 팀원은 개발 시작 전에 반드시 숙지해 주세요.

## 🌿 Git 브랜치 전략

우리 프로젝트는 Feature-Driven Workflow를 따릅니다. **main 브랜치로의 직접적인 Push는 절대 금지**합니다. (단, 초기 프로젝트 세팅 및 공통 엔티티 구성은 예외로 main에 직접 반영되었습니다.)

모든 기능 개발은 브랜치 생성 → PR 생성 → 리뷰 → Squash and Merge 순서로 진행합니다.

### 📌 브랜치 명명 규칙

`종류/기능명` 형태로 생성하며, 단어 구분은 하이픈(`-`)을 사용합니다.

| 종류 | 설명 | 예시 |
|---|---|---|
| feature | 새로운 기능 구현 | `feature/cart-crud`, `feature/order-create` |
| fix | 버그 및 에러 수정 | `fix/db-connection`, `fix/credit-calculation` |
| docs | 문서 수정 (README, API 명세 등) | `docs/readme-update` |
| refactor | 코드 리팩토링 | `refactor/dto-separation` |
| chore | 빌드, 설정, 패키지 등 잡일 | `chore/gitignore-update` |

⚠️ **주의**: 브랜치를 생성하기 전에 항상 main 브랜치에서 `git pull`을 진행하여 최신 상태를 유지하세요.

```bash
git checkout main
git pull origin main
git checkout -b feature/기능명
```

## 💬 커밋 메시지 컨벤션 (Commit Convention)

커밋 메시지는 다른 팀원이 변경 사항을 쉽게 알아볼 수 있도록 아래 규칙을 준수합니다.

### 📌 메시지 구조

```
[태그]: 변경 내용 요약 (한글로 간결하게)
```

### 📌 태그 종류

| 태그 | 설명 |
|---|---|
| `[feat]` | 새로운 기능 추가 |
| `[fix]` | 버그 수정 |
| `[chore]` | 빌드, 설정, 라이브러리 등 잡일 |
| `[docs]` | 문서 수정 |
| `[refactor]` | 코드 리팩토링 (기능 변화 없음) |
| `[style]` | 코드 포맷팅, 세미콜론 누락 등 |
| `[test]` | 테스트 코드 추가/수정 |

### 📌 예시

```
[feat]: 장바구니 상품 추가 API 구현
[fix]: 크레딧 차감 시 잔액 검증 로직 오류 수정
[chore]: ERD 기반 엔티티, 레포지토리, 더미데이터 추가
```

## 🔀 PR (Pull Request) 규칙

- 기능 개발이 끝나면 `main`을 대상으로 PR을 생성합니다.
- **리뷰**: 팀원 1명 이상의 Approve를 받은 후 Merge합니다.
- **Merge 방식**: Squash and Merge를 사용하여 커밋 히스토리를 깔끔하게 유지합니다.
- PR 제목은 커밋 컨벤션과 동일하게 `[태그]: 요약` 형식을 따릅니다.
- PR 본문에는 작업 내용과 확인이 필요한 부분을 간단히 작성합니다.

## 🗂 패키지 구조

도메인형 패키지 구조를 따릅니다.

```
com.mutsa.delivery
├── common/entity      (BaseTimeEntity 등 공통 요소)
├── user                (User)
├── category             (Category)
├── store                (Store, Menu, OptionGroup, Option)
├── cart                 (Cart, CartItem, CartItemOption)
├── order                (Order, OrderStore, OrderItem, OrderItemOption)
└── credit               (CreditTransaction)
```

## ⚙️ 로컬 개발 환경 세팅

### 1. 레포지토리 클론 및 최신 코드 반영

```bash
git clone https://github.com/leehanjay/mutsa-vacation-session.git
cd mutsa-vacation-session
git pull origin main
```

### 2. 로컬 MySQL에 DB 생성

MySQL Workbench 또는 터미널에서 실행합니다.

```sql
CREATE DATABASE mutsa_delivery CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 3. 환경변수 설정 (IntelliJ Run Configuration)

`Run/Debug Configurations` → `Environment variables`에 아래 값을 추가합니다. (각자 본인 로컬 MySQL 계정 정보로 입력)

```
DB_URL=jdbc:mysql://localhost:3306/mutsa_delivery?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
DB_USERNAME=root
DB_PASSWORD=본인_로컬_MySQL_비밀번호
```

> `application.yaml`에는 실제 값이 아닌 `${DB_URL}` 형태의 변수만 존재하므로, 반드시 각자의 IntelliJ 실행 설정에 환경변수를 넣어야 서버가 정상 동작합니다.

### 4. 서버 실행 확인

IntelliJ에서 `DeliveryApplication` 실행 후 콘솔에 아래가 뜨면 정상입니다.

- Hibernate DDL(`create table ...`) 로그 출력
- `Started DeliveryApplication` 로그 출력
- (더미데이터 확인) MySQL에서 `SELECT * FROM users;` 실행 시 데이터 존재

## 🔑 인증 관련 안내

이번 과제에서는 로그인/회원가입 기능을 구현하지 않습니다. 모든 API는 **user_id = 1 (더미 유저)** 를 현재 로그인 사용자로 간주하고 동작합니다.

## 📋 API 담당 분배

| 담당 | 도메인 | API |
|---|---|---|
| 이한재 | cart, order | `GET /carts`, `POST /carts/items`, `PATCH /carts/items/{id}`, `DELETE /carts/items/{id}`, `POST /orders` |
| 송승환 | store, menu, category, user, credit | `GET /stores`, `GET /stores/{id}`, `GET /categories`, `GET /users/me`, `POST /credits/charge` |

⚠️ **User 엔티티는 공통 자산**입니다. 조회/참조만 하고, 구조 변경이 필요하면 반드시 팀원과 논의 후 한 명만 수정합니다.
