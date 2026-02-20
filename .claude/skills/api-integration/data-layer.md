# Data Layer 상세 가이드

## Remote Model

### 위치 및 네이밍

- 위치: `data/source/remote/model/<feature>/`
- 모든 Remote Model은 `@Serializable`을 사용한다 (kotlinx.serialization)
- Domain Model과 구분하기 위해 `Remote` 접두사 사용

### Remote Model (응답 겸용)

서버 응답을 그대로 매핑하는 모델. API 응답값으로 직접 사용할 수 있다.
- 날짜는 서버 형식 그대로 `String`으로 받는다 (변환은 Mapper 담당)
- nullable 여부는 서버 명세에 맞춘다

```kotlin
// data/source/remote/model/ledger/RemoteLedger.kt
@Serializable
data class RemoteLedger(
    val ledgerId: Long,
    val amount: Long,
    val type: RemoteLedgerType,
    val category: RemoteLedgerCategory,
    val description: String?,       // 서버에서 null 가능
    val occurredOn: String,         // 서버 날짜 형식 그대로 "2025-01-15"
    val paymentMethod: RemotePaymentMethod,
    val memo: String?,
)
```

### Request

- 네이밍: `<Feature><동사>Request` (예: `LedgerCreateRequest`, `LedgerEditRequest`)
- 서버에 보내는 값이므로 Remote 타입 사용 (예: `RemoteLedgerType`, `String` 날짜)

```kotlin
// data/source/remote/model/ledger/LedgerCreateRequest.kt
@Serializable
data class LedgerCreateRequest(
    val amount: Long,
    val type: RemoteLedgerType,
    val category: RemoteLedgerCategory,
    val description: String,
    val occurredOn: String,
    val paymentMethod: RemotePaymentMethod,
    val memo: String?,
)

// data/source/remote/model/ledger/LedgerEditRequest.kt
@Serializable
data class LedgerEditRequest(
    val amount: Long,
    val type: RemoteLedgerType,
    val category: RemoteLedgerCategory,
    val description: String,
    val occurredOn: String,
    val paymentMethod: RemotePaymentMethod,
    val memo: String?,
)
```

### Response

기존 `Remote<Model>`을 직접 사용할 수 없는 경우에만 별도 Response 클래스를 만든다.
- 네이밍: `<ModelName>Response`, 리스트면 `<ModelName>sResponse`
- 서버 필드명이 코틀린 프로퍼티명과 다르면 `@SerialName`으로 매핑

```kotlin
// data/source/remote/model/ledger/LedgersResponse.kt
@Serializable
data class LedgersResponse(
    @SerialName("result")       // 서버 필드명 "result" → 프로퍼티명 "ledgers"
    val ledgers: List<RemoteLedger>,
    @SerialName("start")
    val start: String,
    @SerialName("end")
    val end: String,
)
```

```kotlin
// data/source/remote/model/auth/LoginResponse.kt
@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
```

---

## Remote Enum

### 규칙

- 관련 enum은 하나의 파일에 모아둔다 (네이밍: `<Feature>Enums.kt`)
- `@Serializable` + `@SerialName("UPPER_SNAKE_CASE")`으로 서버 값 매핑
- enum 값 이름은 PascalCase로 작성한다
- `@SerialName` 값은 서버 API 명세의 문자열과 정확히 일치해야 한다

```kotlin
// data/source/remote/model/ledger/LedgerEnums.kt

@Serializable
enum class RemoteLedgerType {
    @SerialName("INCOME")
    Income,

    @SerialName("EXPENSE")
    Expense,
}

@Serializable
enum class RemoteLedgerCategory {
    @SerialName("FOOD")
    Food,

    @SerialName("TRANSPORT")
    Transport,

    @SerialName("HOUSING")
    Housing,

    @SerialName("SHOPPING")
    Shopping,

    @SerialName("HEALTH_MEDICAL")
    HealthMedical,

    @SerialName("EDUCATION_SELF_DEVELOPMENT")
    EducationSelfDevelopment,

    @SerialName("LEISURE_HOBBY")
    LeisureHobby,

    @SerialName("SAVINGS_FINANCE")
    SavingFinance,

    @SerialName("SALARY")
    Salary,

    @SerialName("SIDE_INCOME")
    SideIncome,

    @SerialName("BONUS")
    Bonus,

    @SerialName("ALLOWANCE")
    Allowance,

    @SerialName("PART_TIME")
    PartTimeIncome,

    @SerialName("FINANCIAL_INCOME")
    FinancialIncome,

    @SerialName("DUTCH_PAY")
    SplitBill,

    @SerialName("TRANSFER")
    Transfer,

    @SerialName("OTHER")
    Other,
}

@Serializable
enum class RemotePaymentMethod {
    @SerialName("BANK_TRANSFER")
    BankTransfer,

    @SerialName("CREDIT_CARD")
    CreditCard,

    @SerialName("CASH")
    Cash,

    @SerialName("DEBIT_CARD")
    DebitCard,
}
```

---

## Retrofit API Interface

### 위치 및 네이밍

- 위치: `data/source/remote/api/`
- 네이밍: `<Domain>Api` (예: `LedgerApi`, `UserApi`)
- 기존 도메인에 속하면 기존 interface에 함수 추가

### 규칙

- 모든 함수는 `suspend`
- 응답값은 Remote Model 타입 사용 (Domain Model 사용 금지)
- Retrofit 어노테이션: `@GET`, `@POST`, `@PUT`, `@DELETE`
- Path 파라미터: `@Path`, Query 파라미터: `@Query`, Body: `@Body`

```kotlin
// data/source/remote/api/LedgerApi.kt
interface LedgerApi {

    @GET("ledgers/summary")
    suspend fun getLedgerSummary(
        @Query("start") from: String,
        @Query("end") to: String,
    ): LedgersResponse

    @GET("ledgers/{ledgerId}")
    suspend fun getLedger(
        @Path("ledgerId") id: Long,
    ): RemoteLedger

    @POST("ledgers")
    suspend fun createLedger(
        @Body request: LedgerCreateRequest,
    ): RemoteLedger

    @PUT("ledgers/{ledgerId}")
    suspend fun editLedger(
        @Path("ledgerId") ledgerId: Long,
        @Body request: LedgerEditRequest,
    ): RemoteLedger

    @DELETE("ledgers/{ledgerId}")
    suspend fun deleteLedger(
        @Path("ledgerId") id: Long,
    )
}
```

---

## Mapper

### 위치 및 네이밍

- 위치: `data/mapper/`
- 네이밍: `<DomainModelName>Mapper.kt` (예: `LedgerMapper.kt`)
- 확장 함수로 작성한다

### 변환 방향

| 상황 | 제공할 변환 |
|------|------------|
| API만 사용 (Room 없음) | `Remote.toDomain()`, `Domain.toRemote()` |
| API + Room 사용 | 위 + `Remote.toEntity()`, `Entity.toDomain()`, `Domain.toEntity()` |

### Remote → Domain 변환

- value object로 감싸기 (예: `LedgerId(ledgerId)`, `Money(amount)`)
- 서버 String 날짜 → `LocalDate.parse()`
- nullable 처리 (예: `description ?: category.name`)

```kotlin
// data/mapper/LedgerMapper.kt

fun RemoteLedger.toDomain() = Ledger(
    id = LedgerId(ledgerId),
    type = type.toDomain(),
    amount = Money(amount),
    category = category.toDomain(),
    description = description ?: category.name,
    occurredOn = LocalDate.parse(occurredOn),
    paymentMethod = paymentMethod.toDomain(),
    memo = memo,
)
```

### Enum 양방향 변환 — when() 지향

모든 enum 값을 명시적으로 매핑한다. `when`의 exhaustive 검사로 새 값 추가 시 컴파일 에러로 누락을 방지한다.

```kotlin
// Remote → Domain
fun RemoteLedgerType.toDomain(): LedgerType = when (this) {
    RemoteLedgerType.Income -> LedgerType.Income
    RemoteLedgerType.Expense -> LedgerType.Expense
}

// Domain → Remote
fun LedgerType.toRemote(): RemoteLedgerType = when (this) {
    LedgerType.Income -> RemoteLedgerType.Income
    LedgerType.Expense -> RemoteLedgerType.Expense
}

// Remote → Domain
fun RemotePaymentMethod.toDomain(): PaymentMethod = when (this) {
    RemotePaymentMethod.BankTransfer -> PaymentMethod.BankTransfer
    RemotePaymentMethod.CreditCard -> PaymentMethod.CreditCard
    RemotePaymentMethod.Cash -> PaymentMethod.Cash
    RemotePaymentMethod.DebitCard -> PaymentMethod.DebitCard
}

// Domain → Remote
fun PaymentMethod.toRemote(): RemotePaymentMethod = when (this) {
    PaymentMethod.BankTransfer -> RemotePaymentMethod.BankTransfer
    PaymentMethod.CreditCard -> RemotePaymentMethod.CreditCard
    PaymentMethod.Cash -> RemotePaymentMethod.Cash
    PaymentMethod.DebitCard -> RemotePaymentMethod.DebitCard
}

// Remote → Domain (enum 값이 많은 경우도 동일 패턴)
fun RemoteLedgerCategory.toDomain(): LedgerCategory = when (this) {
    RemoteLedgerCategory.Food -> LedgerCategory.Food
    RemoteLedgerCategory.Transport -> LedgerCategory.Transport
    RemoteLedgerCategory.Housing -> LedgerCategory.Housing
    RemoteLedgerCategory.Shopping -> LedgerCategory.Shopping
    RemoteLedgerCategory.HealthMedical -> LedgerCategory.HealthMedical
    RemoteLedgerCategory.EducationSelfDevelopment -> LedgerCategory.EducationSelfDevelopment
    RemoteLedgerCategory.LeisureHobby -> LedgerCategory.LeisureHobby
    RemoteLedgerCategory.SavingFinance -> LedgerCategory.SavingFinance
    RemoteLedgerCategory.Salary -> LedgerCategory.Salary
    RemoteLedgerCategory.SideIncome -> LedgerCategory.SideIncome
    RemoteLedgerCategory.Bonus -> LedgerCategory.Bonus
    RemoteLedgerCategory.Allowance -> LedgerCategory.Allowance
    RemoteLedgerCategory.PartTimeIncome -> LedgerCategory.PartTimeIncome
    RemoteLedgerCategory.FinancialIncome -> LedgerCategory.FinancialIncome
    RemoteLedgerCategory.SplitBill -> LedgerCategory.SplitBill
    RemoteLedgerCategory.Transfer -> LedgerCategory.Transfer
    RemoteLedgerCategory.Other -> LedgerCategory.Other
}
```

### Room Entity 변환 (Room 사용 시)

Entity는 원시 타입으로 저장한다.
- enum → `.name` (String)
- LocalDate → `.toEpochDay()` (Long)
- 복원 시 `valueOf()`, `ofEpochDay()` 사용

```kotlin
// Remote → Entity
fun RemoteLedger.toEntity() = LedgerEntity(
    id = ledgerId,
    amount = amount,
    type = type.name,
    category = category.name,
    paymentMethod = paymentMethod.name,
    description = description,
    occurredOn = LocalDate.parse(occurredOn).toEpochDay(),
    memo = memo,
)

// Entity → Domain
fun LedgerEntity.toDomain(): Ledger = Ledger(
    id = LedgerId(id),
    type = LedgerType.valueOf(type),
    amount = Money(amount),
    category = LedgerCategory.valueOf(category),
    description = description ?: category,
    occurredOn = LocalDate.ofEpochDay(occurredOn),
    paymentMethod = PaymentMethod.valueOf(paymentMethod),
    memo = memo,
)

// Domain → Entity
fun Ledger.toEntity(): LedgerEntity = LedgerEntity(
    id = id.value,
    type = type.name,
    amount = amount.value,
    category = category.name,
    description = description,
    occurredOn = occurredOn.toEpochDay(),
    paymentMethod = paymentMethod.name,
    memo = memo,
)
```

---

## RepositoryImpl

### 위치 및 네이밍

- 위치: `data/repository/`
- 네이밍: `<Domain>RepositoryImpl` (예: `LedgerRepositoryImpl`)

### 공통 규칙

- `@Inject constructor`로 필요한 API + DAO를 주입받는다
- suspend 함수에서 직접 API 호출한다 (Result 미사용, 에러 처리는 UseCase에서 담당)
- Domain 타입 ↔ Remote 타입 변환은 Mapper 확장 함수 사용

### 패턴 1: API만 사용 (Room 없음)

```kotlin
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
) : UserRepository {

    override suspend fun getProfile(): UserProfile {
        return userApi.getProfile().toDomain()
    }

    override suspend fun updateNickname(nickname: String) {
        userApi.updateNickname(NicknameRequest(nickname))
    }
}
```

### 패턴 2: API + Room 캐시

Room을 캐시로 사용하는 경우, 데이터 흐름은:
- **읽기 (Flow)**: Room → observe → Domain
- **쓰기 (suspend)**: API 호출 → Remote → Entity → Room 저장
- **동기화**: API 호출 → Remote → Entity → Room 일괄 저장

```kotlin
class LedgerRepositoryImpl @Inject constructor(
    private val ledgerDao: LedgerDao,
    private val ledgerApi: LedgerApi,
) : LedgerRepository {

    // ── Flow: Room에서 observe ──
    override fun observeLedgers(from: LocalDate, to: LocalDate): Flow<List<Ledger>> {
        return ledgerDao
            .observeLedgers(fromEpochDay = from.toEpochDay(), toEpochDay = to.toEpochDay())
            .map { entities -> entities.map { it.toDomain() } }
            .distinctUntilChanged()
    }

    override fun observeLedger(ledgerId: Long): Flow<Ledger> {
        return ledgerDao.observeLedger(ledgerId)
            .map { entity -> entity.toDomain() }
            .filterNotNull()
            .distinctUntilChanged()
    }

    // ── suspend: 동기화 (API → Room) ──
    override suspend fun ensureSynced(from: LocalDate, to: LocalDate) {
        val remoteLedgers = ledgerApi.getLedgerSummary(
            from = from.toString(),
            to = to.toString(),
        ).ledgers
        val entities = remoteLedgers.map { it.toEntity() }
        ledgerDao.insertAll(entities)
    }

    override suspend fun syncLedger(id: Long) {
        val remoteLedger = ledgerApi.getLedger(id)
        ledgerDao.insert(remoteLedger.toEntity())
    }

    // ── suspend: CRUD ──
    override suspend fun createLedger(
        amount: Long,
        type: LedgerType,
        category: LedgerCategory,
        description: String,
        occurredOn: LocalDate,
        paymentMethod: PaymentMethod,
        memo: String?,
    ) {
        val request = LedgerCreateRequest(
            amount = amount,
            type = type.toRemote(),
            category = category.toRemote(),
            description = description,
            occurredOn = occurredOn.toString(),
            paymentMethod = paymentMethod.toRemote(),
            memo = memo,
        )
        val remoteLedger = ledgerApi.createLedger(request)
        ledgerDao.insert(remoteLedger.toEntity())
    }

    override suspend fun getLedger(ledgerId: Long): Ledger {
        return ledgerDao.getLedger(ledgerId).toDomain()
    }

    override suspend fun editLedger(
        ledgerId: Long,
        amount: Long,
        type: LedgerType,
        category: LedgerCategory,
        description: String,
        occurredOn: LocalDate,
        paymentMethod: PaymentMethod,
        memo: String?,
    ) {
        val request = LedgerEditRequest(
            amount = amount,
            type = type.toRemote(),
            category = category.toRemote(),
            description = description,
            occurredOn = occurredOn.toString(),
            paymentMethod = paymentMethod.toRemote(),
            memo = memo,
        )
        val remoteLedger = ledgerApi.editLedger(ledgerId, request)
        ledgerDao.insert(remoteLedger.toEntity())
    }

    override suspend fun deleteLedger(id: Long) {
        ledgerApi.deleteLedger(id)
        ledgerDao.delete(id)
    }
}
```
