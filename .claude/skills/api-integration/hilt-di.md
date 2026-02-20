# Hilt DI 바인딩 상세 가이드

## NetworkModule — API Provide

### 위치 및 규칙

- 위치: `data/di/NetworkModule.kt`
- 네이밍: `provide<ServiceName>Api`
- 새 API 추가 시, 파일 내부 함수들 **최하단에** 추가한다
- `@Provides` + `@Singleton` 사용
- `retrofit.create(<Api>::class.java)` 패턴

### 현재 구조

```kotlin
// data/di/NetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // ... Json, OkHttpClient, Retrofit 등 인프라 설정 ...

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideLedgerApi(retrofit: Retrofit): LedgerApi {
        return retrofit.create(LedgerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    // ↑ 새 API는 여기 최하단에 추가
}
```

### 새 API 추가 예시

```kotlin
// 새 도메인 "Verdict"의 API 추가 시
@Provides
@Singleton
fun provideVerdictApi(retrofit: Retrofit): VerdictApi {
    return retrofit.create(VerdictApi::class.java)
}
```

---

## RepositoryModule — Repository 바인딩

### 위치 및 규칙

- 위치: `data/di/RepositoryModule.kt`
- `@Binds` + `@Singleton` 을 기본으로 사용한다
- Repository는 stateless하며 API/DAO를 위임만 하므로 인스턴스를 여러 개 만들 필요가 없다
- 불필요한 객체 생성을 방지하고 일관성을 확보한다

### 현재 구조

```kotlin
// data/di/RepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindNicknameRepository(impl: NicknameRepositoryImpl): NicknameRepository

    @Binds
    @Singleton
    abstract fun bindLedgerRepository(impl: LedgerRepositoryImpl): LedgerRepository
}
```

### 새 Repository 추가 예시

```kotlin
// 새 도메인 "Verdict"의 Repository 바인딩 추가 시
@Binds
@Singleton
abstract fun bindVerdictRepository(impl: VerdictRepositoryImpl): VerdictRepository
```

### 바인딩 네이밍 패턴

- 함수명: `bind<Domain>Repository`
- 파라미터: `impl: <Domain>RepositoryImpl`
- 반환 타입: `<Domain>Repository` (Domain의 interface)