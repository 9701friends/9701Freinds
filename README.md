# 🚀 AI 기반 주문 관리 시스템

이 프로젝트는 **가게 사장님들이 상품을 보다 쉽게 등록하고 관리할 수 있도록 돕는 AI 기반 주문 관리 시스템**을 개발하는 것을 목표로 합니다.

---

## 📌 프로젝트 소개

기존의 상품 등록 및 관리 방식은 복잡하고 시간이 많이 소요되며 입력 실수가 발생할 수 있습니다.  
이 시스템은 **AI를 활용하여 상품 정보 등록을 자동화**하고, **간편하게 상품을 관리할 수 있는 시스템을 제공**합니다.

또한, **추후 소비자가 AI에게 상품 설명을 입력하면, 가게 사장님이 등록한 상품 정보에 기반하여 AI가 메뉴를 추천하는 기능**을 추가하여 시스템의 확장성을 염두에 두었습니다.

---

## 👥 팀원 역할 분담

| 이름 | 역할 | 담당 기능 |
|------|------|----------|
| 노석준 | 리더 | Store |
| 최성민 | 테크 리더 | Order, Product, Review, AI |
| 정아현 | 개발자 | Payment |
| 윤한나 | 개발자 | 공통 응답 및 에러 클래스 정의, 유틸, 시큐리티 및 User 기능 구현 |

---

## 🛠️ 기술 스택

### **Language & Framework**
- Java 17
- Spring Boot 3.4.2

### **Database & ORM**
- **Database** : PostgreSQL
- **ORM** : JPA, Querydsl

> **📌 PostgreSQL을 선택한 이유**
> - 복잡한 쿼리 및 트랜잭션 처리에서 뛰어난 성능 발휘
> - 다양한 데이터 타입 및 확장성 지원으로 높은 유연성 제공
> - 높은 동시성 처리 능력을 갖춰 다수의 사용자 요청을 원활하게 처리

### **API 통신**
- OpenFeign

### **Security & Authentication**
- JWT (JSON Web Token)

> **📌 JWT를 선택한 이유**
> - 사용자 인증 및 정보 교환을 위한 안전한 방식
> - 자체 서명된 토큰을 통해 데이터 무결성 보장
> - 서버 간 상태 저장 없이 인증 관리 가능, 확장성 우수

### **Version Control & Build Tool**
- Git
- Gradle

---

## 📐 ERD

👉 [ERD 보기](https://www.erdcloud.com/d/eW3faqWnDPE6XAqnC)

---

## 🏗️ 서비스 구성 및 실행 방법

### **아키텍처**
![Image](https://github.com/user-attachments/assets/d372ca5f-d37a-4a88-a554-8ce36259d874)

### **로컬 환경 실행 방법**

1. 프로젝트 클론
   ```sh
   git clone https://github.com/9701friends/9701Freinds.git
   ```
2. **application.yml 설정**
   ```yaml
   server:
     port: 8080
   spring:
     application:
       name: AiDelivery
     datasource:
       url: jdbc:postgresql://localhost:5432/{Database}
       username: {username}
       password: {password}
     jpa:
       hibernate:
         ddl-auto: update
       properties:
         hibernate:
           dialect: org.hibernate.dialect.PostgreSQLDialect
   google:
     api:
       key: {apiKey}
   jwt:
     secret:
       key: {jwtSecretKey}
   ```
    - **DB 설정**: PostgreSQL 사용
    - **JPA 설정**: PostgreSQL 드라이버 설치
    - **AI 설정**:
        - [AI Studio](https://aistudio.google.com/prompts/new_chat)에서 API 키 발급
        - `google.api.key:` 프로퍼티에 API 키 적용
    - **JWT 설정**: `jwt.secret.key:` 프로퍼티에 JWT 시크릿 키 적용

3. **의존성 설치 및 빌드**
   ```sh
   ./gradlew build
   ```
4. **PostgreSQL 설치**

### **서버 환경 실행 방법**

1. **jar 파일 생성**
   ```sh
   ./gradlew bootJar
   ```
2. **EC2 인스턴스 생성**
3. **보안 규칙 설정**
4. **Java 17 버전 설치**
5. **PostgreSQL 설치**
6. **jar 파일 실행**
   ```sh
   java -jar AiDelivery-0.0.1-SNAPSHOT.jar
   ```

---

## 📖 API 문서

API 문서 및 엔드포인트 명세는 별도 문서에서 확인할 수 있습니다.  
추후 API 명세를 추가할 예정입니다.

---

