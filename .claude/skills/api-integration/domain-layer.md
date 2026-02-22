# Domain Layer 상세 가이드

## Domain Model

### 위치 및 네이밍

- 위치: `domain/model/<feature>/`
- 네이밍: 접두사 없이 순수한 이름 (예: `Ledger`, `User`)
- 한 파일에 관련 모델을 모아둘 수 있다

### Value Object

ID, 금액 등 의미 있는 원시값은 `@JvmInline value class`로 감싼다.
필요 시 `init` 블록에서 유효성 검증을 수행한다.

```kotlin
// domain/model/ledger/Ledger.kt

@JvmInline
value class LedgerId(val value: Long)

@JvmInline
value class Money(val value: Long) {
    init {
        require(value >= 0) { "Money must be >= 0" }
    }
}
```

### Data Class

Domain Model은 비즈니스 관점의 타입을 사용한다.
- 날짜: `LocalDate` (서버의 String 아님)
- ID: value object (`LedgerId`, 원시 `Long` 아님)
- 금액: value object (`Money`, 원시 `Long` 아님)

```kotlin
data class Ledger(
    val id: LedgerId,
    val type: LedgerType,
    val amount: Money,
    val category: LedgerCategory,
    val description: String,
    val occurredOn: LocalDate,
    val paymentMethod: PaymentMethod,
    val memo: String? = null,
)
```

### Domain Enum

`@SerialName` 없이 순수하게 유지한다. 서버 값 매핑은 Data Layer의 Remote Enum이 담당한다.

```kotlin
enum class LedgerType { Income, Expense }

enum class PaymentMethod {
    BankTransfer, CreditCard, Cash, DebitCard
}

enum class LedgerCategory {
    // 지출
    Food, Transport, Housing, Shopping,
    HealthMedical, EducationSelfDevelopment, LeisureHobby, SavingFinance,

    // 수입
    Salary, SideIncome, Bonus, Allowance,
    PartTimeIncome, FinancialIncome, SplitBill, Transfer,

    // 기타
    Other,
}
```

### 유틸리티 확장 함수

도메인 모델에 대한 집계/변환 로직은 같은 파일 하단에 확장 함수로 작성할 수 있다.

```kotlin
data class LedgerSummary(
    val totalIncome: Long,
    val totalExpense: Long,
)

fun List<Ledger>.summarize(): LedgerSummary = LedgerSummary(
    totalIncome = filter { it.type == LedgerType.Income }.sumOf { it.amount.value },
    totalExpense = filter { it.type == LedgerType.Expense }.sumOf { it.amount.value },
)
```

---

## Domain Repository Interface

### 위치 및 네이밍

- 위치: `domain/repository/`
- 네이밍: `<Domain>Repository` (예: `LedgerRepository`)

### 생성 기준

- 기존 도메인(예: Ledger)에 속하는 API → 기존 `LedgerRepository`에 함수 추가
- 새 도메인 → 새 `<Domain>Repository` interface 생성

### 규칙

- **Domain 타입만 사용** — Remote Model, Entity 사용 금지
- suspend 함수는 Result로 감싸지 않는다 (에러 처리는 UseCase 담당)
- Flow 반환 함수는 Room 캐시 기반 observe 패턴에 사용

```kotlin
// domain/repository/LedgerRepository.kt
interface LedgerRepository {

    // ── Flow: Room 캐시 기반 observe ──
    fun observeLedgers(from: LocalDate, to: LocalDate): Flow<List<Ledger>>
    fun observeLedger(ledgerId: Long): Flow<Ledger>

    // ── suspend: 동기화 ──
    suspend fun ensureSynced(from: LocalDate, to: LocalDate)
    suspend fun syncLedger(id: Long)

    // ── suspend: CRUD ──
    suspend fun createLedger(
        amount: Long,
        type: LedgerType,
        category: LedgerCategory,
        description: String,
        occurredOn: LocalDate,
        paymentMethod: PaymentMethod,
        memo: String?,
    )

    suspend fun getLedger(ledgerId: Long): Ledger

    suspend fun editLedger(
        ledgerId: Long,
        amount: Long,
        type: LedgerType,
        category: LedgerCategory,
        description: String,
        occurredOn: LocalDate,
        paymentMethod: PaymentMethod,
        memo: String?,
    )

    suspend fun deleteLedger(id: Long)
}
```

---

## Domain UseCase

### 위치 및 네이밍

- 위치: `domain/usecase/<feature>/`
- 네이밍: `<동사><대상>UseCase`
  - 예: `CreateLedgerUseCase`, `GetLedgerUseCase`, `ObserveLedgersByDayUseCase`, `DeleteLedgerUseCase`
- 파일 1개당 UseCase 1개

### 공통 규칙

- `@Inject constructor`로 Repository 주입
- `operator fun invoke()` 사용
- **반환값은 반드시 명시적으로 선언**한다 (타입 추론에 맡기지 않음)

### 패턴 1: suspend + 반환값 없음 (Result<Unit>)

```kotlin
// domain/usecase/ledger/CreateLedgerUseCase.kt
class CreateLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(
        amount: Long,
        type: LedgerType,
        category: LedgerCategory,
        description: String,
        occurredOn: LocalDate,
        paymentMethod: PaymentMethod,
        memo: String?,
    ): Result<Unit> = runSuspendCatching {
        ledgerRepository.createLedger(
            amount = amount,
            type = type,
            category = category,
            description = description,
            occurredOn = occurredOn,
            paymentMethod = paymentMethod,
            memo = memo,
        )
    }
}
```

### 패턴 2: suspend + 반환값 있음 (Result<Model>)

```kotlin
// domain/usecase/ledger/GetLedgerUseCase.kt
class GetLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(id: LedgerId): Result<Ledger> = runSuspendCatching {
        ledgerRepository.getLedger(id.value)
    }
}
```

### 패턴 3: Flow 반환 (runSuspendCatching 미사용, suspend 아님)

```kotlin
// domain/usecase/ledger/ObserveLedgersByDayUseCase.kt
class ObserveLedgersByDayUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    operator fun invoke(date: LocalDate): Flow<List<Ledger>> {
        return ledgerRepository.observeLedgers(date, date)
    }
}
```

```kotlin
// domain/usecase/ledger/ObserveLedgerUseCase.kt
class ObserveLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    operator fun invoke(id: LedgerId): Flow<Ledger> {
        return ledgerRepository.observeLedger(id.value)
    }
}
```

### 패턴 4: suspend + 부수효과만 (동기화 등)

```kotlin
// domain/usecase/ledger/EnsureLedgersSyncedUseCase.kt
class EnsureLedgersSyncedUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(from: LocalDate, to: LocalDate): Result<Unit> = runSuspendCatching {
        ledgerRepository.ensureSynced(from, to)
    }
}
```

### runSuspendCatching 참고

- import: `com.smtm.pickle.domain.common.utils.runSuspendCatching`
- `CancellationException`을 rethrow하므로 코루틴 취소에 안전하다
- Flow를 반환하는 UseCase에서는 사용하지 않는다
