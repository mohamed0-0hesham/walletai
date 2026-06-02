This is a Kotlin Multiplatform project targeting Android, iOS.

I'll build a proper data layer with per-feature repositories (ISP), entity/mapper separation, swappable data sources, and a small DI graph. Then I'll wire it up and remove the god repository.
Starting with the domain-layer repository interfaces (split by aggregate, ISP-friendly):
Now delete the old god repository to keep the architecture clean:
Now the data layer. Entities (DB-shaped, separate from domain models):
Mappers (entity ↔ domain):
LocalDataSource interfaces (the swap point — replace these to migrate to SQLDelight, Room, etc.):
In-memory data source implementations (will be swapped for SQLDelight/Room later):
Now repository implementations — they depend only on data source interfaces (DIP):
DI graph — single composition root for the data layer:
Now update the ViewModels to use the new repos via coroutines:
Update Installments and Reports VMs:
Now update Reports VM in its file:
Now Settings VM:
Now update App.kt to wire DataGraph + a coroutine scope:
Now AddTransactionScreen needs to handle the nullable suggestion + use the new `save()` method:
Now wire the screen's Save button to call `viewModel.save`:
Now update all previews to use DataGraph + scope:
Add a small preview helper and update each preview. First, dashboard:
Now build:
Build is clean. Here's the new database layer architecture:

```
domain/
  model/Models.kt                            ← unchanged: pure entities
  repository/
    UserRepository.kt                        ← one tiny interface per aggregate (ISP)
    BalanceRepository.kt
    TransactionRepository.kt
    InstallmentRepository.kt
    AnalyticsRepository.kt
    AiSuggestionRepository.kt

data/
  entity/Entities.kt                         ← storage-shaped rows (UserEntity, TransactionEntity, …)
  mapper/Mappers.kt                          ← entity ↔ domain conversion in one place
  source/local/
    LocalDataSources.kt                      ← swap point — interfaces only
    inmemory/
      InMemoryUserLocalDataSource.kt         ← drop-in fake; replace with SQLDelight/Room later
      InMemoryBalanceLocalDataSource.kt
      InMemoryTransactionLocalDataSource.kt
      InMemoryInstallmentLocalDataSource.kt
      InMemoryAnalyticsLocalDataSource.kt
      InMemoryAiSuggestionLocalDataSource.kt
  repository/
    UserRepositoryImpl.kt                    ← depends only on the data source interface (DIP)
    BalanceRepositoryImpl.kt
    TransactionRepositoryImpl.kt
    InstallmentRepositoryImpl.kt
    AnalyticsRepositoryImpl.kt
    AiSuggestionRepositoryImpl.kt
  di/DataGraph.kt                            ← single composition root for the data layer
```

**SOLID applied**

- **SRP**: every file does one thing — one repo, one source, one mapper namespace.
- **OCP**: add a new storage tech by writing a new `XxxLocalDataSource` and a new `DataGraph.sqlDelight(...)` factory; nothing existing has to change.
- **LSP**: data-source interfaces are pure contracts — any impl is interchangeable.
- **ISP**: replaced the old god `WalletRepository` (12 unrelated methods) with six small repos. Each VM depends only on what it actually uses.
- **DIP**: VMs and `RepositoryImpl`s depend on **interfaces only** (`UserRepository`, `UserLocalDataSource`); concrete classes are wired exclusively in `DataGraph`.

**Async by default** — every repo and data-source method is `suspend`, matching what a real driver returns. ViewModels now take a `CoroutineScope` and load data in `init { scope.launch { ... } }`.

**Migration path to a real DB** (e.g. SQLDelight):

1. Add the SQLDelight gradle plugin + `.sq` schema files.
2. Implement `class SqlDelightTransactionLocalDataSource(private val db: WalletDb) : TransactionLocalDataSource { … }` (and so on per source).
3. Add `DataGraph.sqlDelight(driver: SqlDriver) = build(userLocal = SqlDelight…, …)`.
4. In `App.kt`, swap `DataGraph.inMemory()` for `DataGraph.sqlDelight(driver)`.

No screen, no ViewModel, no domain model has to change. The `Entities.kt` schema becomes the SQL schema; the mappers stay. Same story for Room or a network-only/API-cache combo — the only thing that varies is which `…LocalDataSource` implementation goes into the `DataGraph`.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…