---
name: create-pr
description: 현재 브랜치와 develop의 차이를 분석하여 코드 리뷰를 수행한 뒤, 프로젝트 PR 템플릿에 맞는 설명을 작성하고 Pull Request를 생성합니다. "PR 만들어줘", "pull request 생성", "PR 올려줘", "PR 작성" 등의 요청에 응답합니다.
---

# create-pr Skill

## 역할

1. 현재 브랜치와 `develop` 브랜치의 차이를 분석한다.
2. **자체 코드 리뷰**를 수행하여 크리티컬 문제를 판별한다.
3. 크리티컬 문제가 있으면 → 원인과 수정 방향을 제시하고 **PR 생성을 중단**한다.
4. 크리티컬 문제가 없으면 → PR 템플릿을 채워 사용자에게 보여주고, **사용자 확인 후** PR을 생성한다.

**PR 생성 전 반드시 사용자 확인을 받는다. 무단으로 `gh pr create`를 실행하지 않는다.**

---

## 실행 절차

### Step 1: 브랜치 확인

```bash
git branch --show-current
```

- 현재 브랜치가 `develop` 또는 `main`이면 즉시 중단한다:
  > 현재 브랜치가 `{브랜치명}`입니다. feature/fix 브랜치에서 PR을 생성해주세요.

### Step 1.5: 원격 브랜치 최신화

```bash
git fetch origin
```

로컬 `develop`이 stale한 경우 커밋 목록·diff·PR 본문이 오래된 기준으로 계산되는 것을 방지한다.
이후 모든 비교 기준은 `origin/develop`을 사용한다.

### Step 2: develop과의 커밋 목록 확인

```bash
git log origin/develop..HEAD --oneline
```

- 커밋이 없으면 즉시 중단한다:
  > develop 브랜치와 차이가 없습니다. 변경사항을 커밋 후 다시 시도해주세요.

### Step 3: develop과의 전체 diff 확인

```bash
git diff origin/develop...HEAD
```

변경된 파일 목록과 내용을 파악한다.

### Step 3.5: Kotlin 컴파일 검증

```bash
./gradlew compileDebugKotlin 2>&1 | tail -30
```

- 빌드 **실패** 시 → 오류 내용을 출력하고 PR 생성을 **중단**한다.
- 빌드 **성공** 시 → Step 5-B의 PR 체크리스트에서 `🛠️ 빌드 성공` 항목을 자동으로 체크한다.

> `compileDebugKotlin`을 선택한 이유: 전체 `build`보다 빠르고, Kotlin 컴파일 오류·import 오류·타입 불일치를 모두 잡을 수 있다.

### Step 4: 자체 코드 리뷰

아래 **크리티컬 기준**으로 diff를 검토한다.
크리티컬 문제가 1개라도 발견되면 → **Step 5-A**로 이동.
모두 통과하면 → **Step 5-B**로 이동.

#### 크리티컬 기준 (Android / Kotlin)

| 분류 | 체크 항목 |
|------|-----------|
| **보안** | 하드코딩된 비밀번호, API 키, 토큰 |
| **크래시** | 강제 언래핑(`!!`) 남용, NPE 위험 경로 |
| **메모리 누수** | ViewModel/Coroutine에서 lifecycle 미고려, Context 장기 보유 |
| **비즈니스 로직** | UseCase/Repository 계층 역할 혼용, 도메인 규칙 위반 |
| **네트워크** | 에러 핸들링 완전 누락 (try-catch도 없고 Result도 없는 경우) |
| **빌드 파괴** | import 오류, 미정의 참조, 시그니처 불일치 |
| **디자인 시스템** | `PickleTheme` 미사용, 하드코딩된 `Color`/`TextStyle` (dp 수치값은 애니메이션 offset 등 디자인 토큰이 없는 특수 목적의 경우 예외 허용) |
| **아키텍처** | Composable에서 직접 Repository 호출, UI 레이어에서 데이터 직접 파싱 |

#### 비크리티컬 (참고만, PR 차단 안 함)

- 변수명 컨벤션, 불필요한 주석, 중복 로직, 미사용 import 등

---

### Step 5-A: 크리티컬 문제 보고 (PR 중단)

아래 형식으로 출력하고 종료한다. PR은 생성하지 않는다.

```
## 코드 리뷰 결과 — 크리티컬 문제 발견

PR 생성을 중단합니다. 아래 문제를 수정한 뒤 다시 시도해주세요.

---

### 🔴 [문제 제목]
- **파일**: `경로/파일명.kt` (Line N)
- **원인**: [왜 문제인지 설명]
- **수정 방향**: [어떻게 고쳐야 하는지]

---

### 🔴 [문제 제목]
...
```

---

### Step 5-B: PR 설명 초안 생성

`.github/pull_request_template.md` 템플릿을 기준으로 PR 본문을 작성한다.

#### 템플릿 작성 규칙

```markdown
### ♟️ Issue
- Closes #[이슈 번호] (이슈 번호를 알 수 없으면 `-` 로 남긴다)

### ✨ 주요 변경 사항
- [변경 사항 1]: [간결한 설명]
- [변경 사항 2]: [간결한 설명]
(develop..HEAD 커밋과 diff를 기반으로 실제 변경된 내용 작성)

### ✅ 체크리스트
- [x] 🌱 merge 브랜치 확인
- [x] 🛠️ 빌드 성공
- [ ] 💬 관련 이슈 연결 ([Closes | Fixes | Resolves] #123)

### 🔍 중점 리뷰 사항
- [리뷰어가 집중해서 봐야 할 부분]
(코드 리뷰 중 비크리티컬 지적사항 또는 주요 설계 결정 포함)

### 📸 스크린샷
> UI 변경이 있는 경우 스크린샷을 첨부해주세요. 없으면 이 섹션을 삭제해도 됩니다.
```

---

### Step 6: 사용자 확인

아래 형식으로 출력하고 **반드시 사용자의 명시적 승인을 기다린다**:

```
## 코드 리뷰 통과 ✅

비크리티컬 사항이 있다면 함께 표시한다.

---

## PR 생성 준비

**제목**: `[브랜치명에서 추론한 PR 제목]`
**Base**: `develop` ← **Head**: `현재 브랜치명`

**본문 미리보기**:
---
[작성된 PR 본문 전체]
---

위 내용으로 PR을 생성할까요? (Yes / No)
수정이 필요하면 원하는 내용을 말씀해주세요.
```

---

### Step 7: PR 생성

사용자가 **Yes** 또는 **수정 후 재확인**을 하면 아래 순서로 PR을 생성한다.

줄바꿈·따옴표·백틱 등 특수문자가 포함된 멀티라인 Markdown 본문은 `--body` 직접 전달 시 shell quoting 문제가 발생할 수 있으므로, 임시 파일을 사용한다:

```bash
cat > /tmp/pr_body.md << 'EOF'
[작성된 PR 본문 전체]
EOF

gh pr create --base develop --title "[PR 제목]" --body-file /tmp/pr_body.md
```

생성 후 PR URL을 출력한다.

---

## PR 제목 규칙

브랜치명 패턴에서 제목을 추론한다:

| 브랜치 패턴 | 제목 형식 |
|-------------|-----------|
| `feature/xxx` | `feat: xxx` |
| `fix/xxx` | `fix: xxx` |
| `hotfix/xxx` | `hotfix: xxx` |
| `refactor/xxx` | `refactor: xxx` |
| `chore/xxx` | `chore: xxx` |
| 기타 | 커밋 메시지에서 가장 대표적인 것 사용 |

브랜치명의 `/`, `-`, `_`는 공백 또는 적절한 구분으로 변환한다.

---

## 비크리티컬 리뷰 출력 형식

Step 6 확인 단계에서 함께 노출한다:

```
### 🟡 참고 사항 (PR 차단 안 함)
- `파일명.kt` Line N: [내용]
```
