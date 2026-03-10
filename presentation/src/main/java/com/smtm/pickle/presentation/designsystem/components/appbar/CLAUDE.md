# AppBar 패키지 코딩 지침

## 설계 철학

> **"화면은 레이아웃 프리미티브와 직접 대화하지 않는다."**

슬롯 API는 무엇이든 넣을 수 있어 잘못 쓰는 것을 막을 방법이 없다.
Named Preset은 `onBack: () -> Unit`만 받으므로 내부 렌더링은 선택지가 없다.
화면이 프리셋 이름을 보는 것만으로 어떤 앱바인지 즉시 파악할 수 있다.

```kotlin
// 슬롯 API (금지) — "이게 어떤 앱바야?"
PickleAppBarLayout(start = { ... }, center = { ... }, end = { ... })

// Named Preset (권장) — 한 눈에 파악
PickleTitleAppBar(title = title, onBack = onBack, actions = listOf(...))
```

---

## 레이어 구조

```text
Layer 0: PickleAppBarLayout (internal) — 3-zone 레이아웃, statusBarsPadding 내부 처리
Layer 1: component/ Building blocks   — 디자인 시스템 승인 컴포넌트, Named Preset 내부에서만 사용
Layer 2: Named Presets (public)       — 화면이 유일하게 접촉하는 API
```

화면은 Named Preset만 본다. 내부 구현(레이아웃, 빌딩 블록)은 접근 불가.

---

## 핵심 설계 결정 — 변경 금지

| 결정 | 이유 |
|---|---|
| `PickleAppBarLayout`이 `internal` | 화면이 레이아웃 프리미티브에 직접 접근하는 것을 구조적으로 차단 |
| 타이틀 정렬 파라미터 없음 (항상 중앙 고정) | 정렬 선택지가 생기면 의사결정 부담 + 일관성 파괴 |
| `PickleAppBarAction`이 sealed | action은 유한(아이콘 or 텍스트)이므로 sealed가 맞음; 람다 슬롯으로 바꾸면 안 됨 |
| `statusBarsPadding()`을 내부에서 처리 | 호출자가 신경 쓸 필요 없음; Scaffold 내에서도 이중 적용 문제 없음 |

---

## 현재 Named Preset 목록

```kotlin
PickleLogoAppBar(actions: List<PickleAppBarAction> = emptyList())
PickleTitleAppBar(title, onBack, actions = emptyList(), containerColor?)
PickleTitleAppBar(title, actions = emptyList(), containerColor?)   // onBack 없는 오버로드
PickleSearchAppBar(value, onValueChange, onCancel, hint = "")
PickleBackAppBar(onBack, containerColor?)
```

---

## `PickleAppBarAction` sealed interface 사용법

```kotlin
// model/PickleAppBarAction.kt
sealed interface PickleAppBarAction {
    data class Icon(@DrawableRes val icon: Int, val onClick: () -> Unit, val contentDescription: String? = null) : PickleAppBarAction
    data class Text(val label: String, val onClick: () -> Unit) : PickleAppBarAction
}

// 사용 예
PickleTitleAppBar(
    title = "타이틀",
    onBack = onBack,
    actions = listOf(PickleAppBarAction.Icon(R.drawable.ic_app_bar_option, onOption, "옵션"))
)
```

---

## 새 프리셋 추가 규칙

1. appbar 패키지에 `PickleXxxAppBar.kt` 신규 파일 생성
2. `PickleAppBarLayout` + `component/` Building blocks 조합
3. **`PickleTopAppBar.kt`에는 절대 추가하지 말 것** — 레거시 V1 파일, 삭제 예정

---

## 레거시 코드 — 신규 사용 금지

| 파일/타입 | 상태 |
|---|---|
| `PickleTopAppBar.kt` 내 `PickleAppBar()`, `PickleAppBarWithBottomContent()` | V1 deprecated, 삭제 예정 |
| `model/NavigationItem.kt` | 삭제 예정 레거시 모델 |
| `model/AppBarAlignment.kt` | 삭제 예정 레거시 모델 |

---

## 금지 사항 (Do NOT)

- 화면 레이어(`feature`, `app` 모듈)에서 `PickleAppBarLayout` 직접 호출
- `component/` Building blocks를 화면에서 직접 사용
- V1 컴포넌트, 레거시 모델 신규 사용
- `PickleAppBarAction`을 람다 슬롯으로 변경
- 타이틀 정렬 파라미터 추가
