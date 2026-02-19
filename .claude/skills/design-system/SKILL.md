---
name: design-system
description: 프로젝트에서 Compose 디자인 시스템 컴포넌트를 설계하고 구현합니다. 기존 컴포넌트 패턴(네이밍, 파라미터 설계, 모델 분리)을 준수하며, 디자인 시스템은 Layout과 Action에 대해서만 알아야 하고 Presentation 고유의 비즈니스 로직 사용은 지양합니다.
---

# Pickle Design System Guide

## 프로젝트 구조

디자인 시스템 컴포넌트는 아래 경로에 위치합니다:

```
presentation/src/main/java/com/smtm/pickle/presentation/designsystem/
├── theme/
│   ├── color/        # Color, ColorScheme, PickleColors, SemanticColors
│   ├── typography/   # Font(Pretendard), Typography
│   └── dimension/    # Dimensions (spacing, sizing 상수)
├── foundation/       # 유틸리티 레이어
└── components/       # 재사용 가능한 UI 컴포넌트
    └── <category>/
        ├── Pickle<Name>.kt
        └── model/
```

## 컴포넌트 작성 규칙

### 1. 네이밍 컨벤션

- 컴포넌트 함수: `Pickle` + PascalCase (예: `PickleButton`, `PickleButtonV2`)
- 모델 클래스: `Pickle` + PascalCase (예: `PickleButtonType`, `PickleButtonSize`)
- 파일명: 클래스/함수명과 동일하게 PascalCase
- 카테고리 디렉토리: 소문자 (예: `button/`)

### 2. Composable 파라미터 설계 패턴

기존 컴포넌트들이 따르는 파라미터 순서와 패턴을 준수합니다:

```kotlin
@Composable
fun Pickle<ComponentName>(
    // 1. 필수 파라미터 (콘텐츠, 액션)
    title: String,
    onClick: () -> Unit,

    // 2. modifier는 첫 번째 optional 파라미터
    modifier: Modifier = Modifier,

    // 3. 설정/상태 파라미터 (기본값 제공)
    enabled: Boolean = true,
    type: PickleButtonType = PickleButtonType.Primary,
    size: PickleButtonSize = PickleButtonSize.Large,

    // 4. 테마 기본값 파라미터
    color: Color = PickleTheme.colors.base0,
    textStyle: TextStyle = PickleTheme.typography.body1Bold,

    // 5. 슬롯 파라미터 (마지막에 배치, trailing lambda)
    content: @Composable ColumnScope.() -> Unit,
)
```

### 3. 모델 정의 패턴

설정 옵션은 enum 또는 sealed interface로 분리하고, `model/` 하위 디렉토리에 배치합니다:

```kotlin
// enum - 타입 분류
enum class PickleButtonType { Primary, Secondary, Tertiary, Ghost }

// enum - 사이즈 분류
enum class PickleButtonSize { Small, Medium, Large }

// enum + 확장 함수 - 스타일 매핑
@Composable
fun PickleButtonType.toColors(): ButtonColors { ... }

@Composable
fun PickleButtonSize.toSpec(): PickleButtonSizeSpec { ... }
```

### 4. 테마 사용 규칙

- 색상: `PickleTheme.colors.*` 또는 `PickleTheme.semantic.*` 사용
- 타이포그래피: `PickleTheme.typography.*` 사용
- 치수: `Dimensions.*` 객체 상수 사용
- 하드코딩된 Color/TextStyle 값 사용 금지

```kotlin
// Good
color = PickleTheme.colors.primary400
textStyle = PickleTheme.typography.body1Bold
height = Dimensions.appbarHeight

// Bad
color = Color(0xFF2BC4C1)
fontSize = 16.sp
height = 56.dp
```

### 5. Preview 작성

모든 컴포넌트는 `@Preview`를 포함하며, `PickleTheme`으로 감싸야 합니다:

```kotlin
@Preview
@Composable
private fun Pickle<ComponentName>Preview() {
    PickleTheme {
        Pickle<ComponentName>(
            // 대표적인 사용 예시
        )
    }
}
```

## 설계 원칙

### 디자인 시스템이 알아야 할 것 (O)
- Layout: 크기, 간격, 정렬, 배치
- Action: 클릭, 스크롤, 드래그 등 사용자 인터랙션 콜백

### 디자인 시스템이 알면 안 되는 것 (X)
- 비즈니스 로직 (ViewModel, UseCase, Repository)
- 네비게이션 로직 (특정 화면으로의 이동)
- 도메인 엔티티 (domain 모듈의 모델)
- 네트워크/데이터베이스 관련 로직

### 새 컴포넌트 추가 체크리스트

1. `components/<category>/` 디렉토리에 배치
2. `Pickle` 접두사로 네이밍
3. 설정 옵션이 있으면 `model/` 하위에 enum/sealed interface 분리
4. 파라미터 순서 패턴 준수 (필수 -> modifier -> 설정 -> 테마 -> 슬롯)
5. `PickleTheme` 토큰만 사용 (하드코딩 금지)
6. `@Preview` 작성
7. 새로운 Dimension 값이 필요하면 `Dimensions` 객체에 추가