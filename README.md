# 🗓 60 Days Backend Challenge

✅ Day 1 – Project Setup

- Spring Boot + Gradle 프로젝트 초기화

- GitHub 저장소 구성 및 기본 환경 설정

✅ Day 2 – Docker Environment

- Docker 기반 MySQL / Adminer 구성

- application-dev.yml로 개발 환경 분리

✅ Day 3 – Git Flow & Collaboration

- Git Flow 브랜치 전략 적용

- GitHub Actions CI 초안 구성

✅ Day 4 – Layered Architecture

- Controller / Service / Repository 계층 분리

- DTO 기반 요청·응답 구조 설계

✅ Day 5 – JPA Entity & DTO Design

- Entity ↔ DTO 분리 패턴 적용

- JPA 연관관계 매핑 (User, Post)

✅ Day 6 – RESTful CRUD APIs

- RESTful CRUD API 구현

- ResponseEntity를 활용한 HTTP 응답 처리

✅ Day 7 – Validation & Exception Handling

- Bean Validation 적용 (@Valid)

- 공통 예외 처리 구조 구성

✅ Day 8 – Common API Response

- ApiResponse<T> 공통 응답 포맷 도입

- 일관된 API 응답 구조 적용

✅ Day 9 – Testing

- Controller / Service 단위 테스트 작성

- 최신 Spring Boot 테스트 스타일 적용

✅ Day 10 – Swagger / OpenAPI Documentation

- SpringDoc(OpenAPI 3) 기반 API 문서화
- Swagger UI를 통한 API 확인 및 테스트
- Controller 단위 API 설명 명시 (@Tag, @Operation)

✅ Day 11 – Spring Security + JWT 인증
- Spring Security 기반 Stateless 인증 구조 설계
- AuthenticationEntryPoint / AccessDeniedHandler로 401/403 응답 표준화
- 공통 응답 포맷(ApiResponse) 적용

✅ Day 12 – Refresh Token 순환 및 재사용 탐지
- Refresh Token 1회성 사용(One-time use) 구조로 개선
- MockMvc 기반 테스트로 정상/비정상 시나리오 검증

✅ Day 13 – JWT 인증 오류 분기 & 권한 인가 구조 정리
- JWT 인증 필터(JwtAuthenticationFilter)에서 토큰 파싱 실패 사유를 request attribute로 기록하도록 개선
- AuthenticationEntryPoint에서 해당 사유를 읽어 ErrorCode 기반의 표준 ErrorResponse 반환
- 
✅ Day 14 – Role 기반 Authorization & 401/403 표준화
- Access Token에 권한(Role)을 포함
- Spring Security가 권한 기반으로 API 접근을 제어 설정