---
name: recommend-commit-message
description: staged git 변경사항을 분석하여 프로젝트 커밋 컨벤션에 맞는 커밋 메시지 2-3개를 추천합니다. "커밋 메시지 추천", "commit 메시지", "어떻게 커밋할까", "커밋 어떻게 써", "git commit 도움" 등의 요청에 응답합니다. 커밋을 직접 실행하지 않습니다.
---

# recommend-commit Skill

## 역할

staged 변경사항을 분석하여 프로젝트 커밋 컨벤션에 맞는 커밋 메시지 후보 2–3개를 추천한다.
**커밋을 직접 실행하지 않는다.** 최종 선택과 실행은 사용자가 한다.

---

## 실행 절차

매 요청마다 아래 5단계를 순서대로 수행한다.

### Step 1: staged 변경사항 확인

```bash
git diff --staged
```

출력이 비어있으면 즉시 종료하고 다음 메시지를 출력한다:

> staged 변경사항이 없습니다. `git add <파일>` 로 먼저 파일을 staging 해주세요.

### Step 2: unstaged 변경사항 확인 (경고용)

```bash
git diff
```

unstaged 변경사항이 있으면 출력 마지막에 경고를 추가한다 (커밋 메시지 생성은 계속 진행).

### Step 3: 최근 커밋 히스토리 확인

```bash
git log --oneline -10
```

scope 사용 패턴, body 스타일, 자주 쓰이는 type을 파악한다.

### Step 4: 변경사항 분석

- 변경된 파일 목록과 diff 내용을 바탕으로 변경의 **단일 목적**을 파악한다.
- 변경사항이 명확히 2개 이상의 독립적인 목적을 담고 있으면, 커밋 분리를 제안하고 각각의 메시지를 따로 추천한다.
- 단일 목적이면 아래 Step 5로 진행한다.

### Step 5: 커밋 메시지 후보 생성

아래 **커밋 컨벤션**을 적용하여 후보 2–3개를 생성한다.
후보 1은 가장 권장하는 메시지로 선정하고 이유를 간단히 설명한다.

---

## 커밋 컨벤션

### 형식 템플릿

```
[type]([scope]): [한 줄 요약]

  - [항목]: [상세 내용]
  - [항목]: [상세 내용]
```

- **scope**는 선택적이며, 최근 히스토리에서 사용 패턴이 있을 때만 추가한다.
- 한 줄 요약은 50자 이내, 명령형 현재 시제 (한국어 또는 영어 프로젝트 관례 따름).
- body(`- [항목]: [상세]`)는 변경이 복잡하거나 이유가 필요할 때만 추가한다.

### Type 표

| type | 사용 상황 |
|------|-----------|
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `refactor` | 동작 변경 없는 코드 구조 개선 |
| `style` | 포매팅, 세미콜론 등 코드 의미 변경 없는 수정 |
| `test` | 테스트 코드 추가/수정 |
| `docs` | 문서, 주석 변경 |
| `chore` | 빌드 스크립트, 패키지 설정, CI 등 |
| `perf` | 성능 개선 |
| `build` | 빌드 시스템, 외부 의존성 변경 |
| `ci` | CI/CD 파이프라인 설정 변경 |
| `revert` | 이전 커밋 되돌리기 |
| `hotfix` | 프로덕션 긴급 수정 |
| `design` | UI/UX 디자인 변경 (로직 무관) |
| `move` | 파일/디렉토리 이동 또는 이름 변경 |
| `remove` | 파일/코드 삭제 |
| `init` | 프로젝트/모듈 초기 설정 |
| `wip` | 진행 중인 작업 (완료되지 않은 커밋) |

### 모호한 경우 판단 기준

- 기능 추가 + 버그 수정이 함께 있으면 → 분리 제안 (각각 `feat`, `fix`)
- 기존 코드를 새 패턴으로 교체했으나 동작이 같으면 → `refactor`
- 파일 이동만 있으면 → `move`
- Gradle 버전/의존성만 변경되면 → `build`
- GitHub Actions, CI 설정만 변경되면 → `ci`

---

## 출력 형식

```
### 분석 요약
[변경된 파일 수, 주요 변경 내용 2-3줄 요약]

---

### 후보 1 ✅ (추천)
```
[type]([scope]): [요약]
  - [항목]: [상세]
```
> 추천 이유: [한 줄]

---

### 후보 2
```
[type]([scope]): [요약]
```

---

### 후보 3 (선택)
```
[type]([scope]): [요약]
```

---

> ⚠️ unstaged 변경사항이 있습니다. 포함할 파일이 있다면 `git add` 후 다시 요청해주세요.
(unstaged가 없으면 이 경고는 생략)
```

---

## Android 경로별 Type 힌트

파일 경로 패턴으로 type을 1차 판단한다. 실제 diff 내용이 우선이며, 아래 표는 참고용이다.

| 경로 패턴 | 우선 고려 type |
|-----------|---------------|
| `*/designsystem/**` | `design`, `feat`, `refactor` |
| `*/presentation/**` (ViewModel, State) | `feat`, `fix`, `refactor` |
| `*/presentation/**` (Composable, Screen) | `feat`, `design` |
| `*/domain/usecase/**` | `feat`, `refactor` |
| `*/domain/model/**` | `feat`, `refactor`, `move` |
| `*/domain/repository/**` (interface) | `feat`, `refactor` |
| `*/data/repository/**` (impl) | `feat`, `fix`, `refactor` |
| `*/data/remote/**` (API, DTO) | `feat`, `fix` |
| `*/di/**` (Hilt module) | `feat`, `refactor`, `chore` |
| `*.gradle`, `*.gradle.kts`, `libs.versions.toml` | `build`, `chore` |
| `.github/workflows/**` | `ci` |
| `*.md`, `*.txt` | `docs` |
| `*Test.kt`, `*Spec.kt` | `test` |
