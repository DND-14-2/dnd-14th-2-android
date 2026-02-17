---
name: api-integration
description: API 명세를 기반으로 Domain Layer(Model, UseCase, Repository Interface)와 Data Layer(RepositoryImpl, Retrofit API, Hilt 바인딩, Remote Model, Mapper)를 일관된 프로젝트 패턴에 맞춰 생성합니다.
---

# API Integration Guide

## 개요

API 명세(endpoint, request, response)를 전달받으면, 아래 규칙에 따라 Domain → Data 순서로 코드를 생성합니다.
각 레이어의 상세 패턴과 예시는 세부 파일을 참조합니다.

## 생성 순서

1. **Domain Model** — 비즈니스 모델 정의
2. **Domain Repository Interface** — 추상 함수 선언
3. **Domain UseCase** — Repository 호출 + 에러 처리
4. **Data Remote Model** — Request/Response/Enum 정의
5. **Data Retrofit API Interface** — API 함수 추가
6. **Data Mapper** — Remote ↔ Domain 변환
7. **Data RepositoryImpl** — API 호출 구현
8. **Hilt DI 바인딩** — NetworkModule, RepositoryModule 업데이트

## 규칙 요약

### Domain Layer (`domain-layer.md` 참조)

| 항목 | 위치 | 네이밍 | 핵심 규칙 |
|------|------|--------|-----------|
| Model | `domain/model/<feature>/` | 접두사 없음 (`Ledger`) | `@JvmInline value class`로 ID/금액 감싸기, enum은 `@SerialName` 없이 순수 유지 |
| Repository | `domain/repository/` | `<Domain>Repository` | Domain 타입만 사용, 기존 도메인이면 함수 추가 |
| UseCase | `domain/usecase/<feature>/` | `<동사><대상>UseCase` | `operator fun invoke`, 반환값 명시 필수 |

### Data Layer (`data-layer.md` 참조)

| 항목 | 위치 | 네이밍 | 핵심 규칙 |
|------|------|--------|-----------|
| Remote Model | `data/source/remote/model/<feature>/` | `Remote` 접두사 (`RemoteLedger`) | `@Serializable` (kotlinx.serialization) |
| Request | 동일 | `<Feature><동사>Request` | `@Serializable` |
| Response | 동일 | `<ModelName>Response` / `<ModelName>sResponse` | 기존 Remote Model 사용 가능하면 그대로 |
| Remote Enum | 동일 | `<Feature>Enums.kt`에 모음 | `@Serializable` + `@SerialName("UPPER_SNAKE")` |
| Retrofit API | `data/source/remote/api/` | `<Domain>Api` | 모든 함수 `suspend`, 응답은 Remote Model 타입 |
| Mapper | `data/mapper/` | `<DomainModel>Mapper.kt` | 확장 함수, `when()` 지향, value object 변환 포함 |
| RepositoryImpl | `data/repository/` | `<Domain>RepositoryImpl` | `@Inject constructor`, Result 미사용, 직접 API 호출 |

### Hilt DI 바인딩 (`hilt-di.md` 참조)

| 항목 | 위치 | 핵심 규칙 |
|------|------|-----------|
| API Provide | `data/di/NetworkModule.kt` | `provide<ServiceName>Api`, 최하단에 추가 |
| Repository Bind | `data/di/RepositoryModule.kt` | `@Binds` + `@Singleton` 기본 |

## 에러 처리 정책

| Layer | 처리 방식 |
|-------|-----------|
| **Data Layer** | Result 미사용. 예외를 그대로 throw |
| **Domain UseCase** | suspend → `runSuspendCatching`으로 `Result<T>` 반환. Flow → 그대로 반환 |
| **Presentation** | `Result`의 `onSuccess`/`onFailure`로 UI 에러 처리 |

- `runSuspendCatching`은 `com.smtm.pickle.domain.common.utils.runSuspendCatching`을 import
- 내부에서 `CancellationException`을 rethrow하므로 코루틴 취소에 안전
