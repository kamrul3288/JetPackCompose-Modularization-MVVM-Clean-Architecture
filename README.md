# Android Clean Architecture — Multi-Module

A production-grade Android application demonstrating **Clean Architecture** with **multi-module Gradle** structure, **Jetpack Compose** UI, and **MVVM** presentation pattern. Built against the latest stable Android toolchain.

<img width="360" height="780" src="https://github.com/user-attachments/assets/07095b75-d19e-4dd3-bd09-1f47fb5cf7ca"/>

---

## Tech Stack

| Layer | Library / Tool | Version |
|---|---|---|
| Language | Kotlin | 2.3.21 |
| Build | Android Gradle Plugin | 9.2.1 |
| Build | Kotlin Symbol Processing (KSP) | 2.3.8 |
| UI | Jetpack Compose BOM | 2026.05.00 |
| UI | Material3 | via BOM |
| UI | Navigation Compose | 2.9.8 |
| UI | Coil (AsyncImage) | 2.7.0 |
| DI | Hilt | 2.59.2 |
| Async | Kotlin Coroutines | 1.11.0 |
| Network | Retrofit | 3.0.0 |
| Network | OkHttp | 5.3.2 |
| Serialization | kotlinx.serialization | 1.11.0 |
| Logging | Timber | 5.0.1 |
| Min SDK | 23 (Android 6.0) | |
| Compile SDK | 37 | |

---

## Architecture

The project follows **Clean Architecture** principles, splitting responsibilities across three strict layers. Dependencies only point inward — outer layers depend on inner layers, never the reverse.

```
┌─────────────────────────────────────────────────────┐
│                 Presentation (features)             │
│         ViewModel · UiState · UiAction              │
│         Smart / Dumb Composable Pattern             │
└──────────────────────┬──────────────────────────────┘
                       │ depends on
┌──────────────────────▼──────────────────────────────┐
│                    Domain (core:domain)             │
│      Entities · Repository Contracts · UseCases    │
│      Resource<T> · DataError · BaseUseCase         │
└──────────────────────┬──────────────────────────────┘
                       │ implements
┌──────────────────────▼──────────────────────────────┐
│                    Data (core:data)                 │
│    Retrofit · DTOs · Mappers · Repository Impls    │
│    NetworkBoundResource · NetworkErrorParser        │
└─────────────────────────────────────────────────────┘
```

### Unidirectional Data Flow (UDF)

Each feature screen follows a strict UDF contract:

```
User Event
    │
    ▼
UiAction (sealed interface)
    │
    ▼
ViewModel.handleAction()
    │  emits
    ▼
UiState (sealed interface)  ──►  Composable re-renders
```

### Smart / Dumb Composable Pattern

```kotlin
// Smart — knows about ViewModel, owns state collection
@Composable
internal fun ProfileScreenRoute(viewModel: ProfileViewModel = hiltViewModel(), ...) {
    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    ProfileScreen(uiState = uiState, onRetry = { viewModel.handleAction(...) }, ...)
}

// Dumb — pure UI, zero ViewModel/Action knowledge, trivially testable
@Composable
private fun ProfileScreen(uiState: ProfileUiState, onRetry: () -> Unit, ...) { ... }
```

---

## Module Graph

```
:app
 ├── :core:di
 ├── :core:domain
 ├── :core:data
 ├── :core:designsystem
 ├── :core:ui
 ├── :common
 ├── :features:repolist
 └── :features:profile

:features:repolist / :features:profile
 ├── :core:domain
 ├── :core:designsystem
 └── :core:ui

:core:data
 ├── :core:domain
 └── :core:di

:core:ui
 ├── :core:designsystem
 └── :core:domain
```

### Module Responsibilities

| Module | Type | Responsibility |
|---|---|---|
| `:app` | Android Application | Entry point, `AppNavigation`, top-level DI wiring |
| `:common` | JVM Library | Shared pure-Kotlin utilities |
| `:build-logic:convention` | Gradle Plugin | Convention plugins — eliminates boilerplate from every module's `build.gradle.kts` |
| `:core:di` | Android Library | Cross-cutting DI: `CoroutineModule`, `@IoDispatcher`, `@AppBaseUrl` qualifiers |
| `:core:domain` | **JVM Library** | Entities, repository contracts, use cases, `Resource<T>`, `DataError`, `ConnectivityObserver` interface. **No Android dependency.** |
| `:core:data` | Android Library | Retrofit API service, DTOs, mappers, `NetworkBoundResource`, `NetworkErrorParser`, `ConnectivityObserverImpl`, repository implementations, Hilt modules |
| `:core:designsystem` | Android Library | Material3 theme, color scheme, typography, shapes, `ScaffoldTopAppbar` |
| `:core:ui` | Android Library | Shared composables, `DataError` → string resource mapping |
| `:features:profile` | Android Library | Profile screen — Smart/Dumb composables, `ProfileViewModel` |
| `:features:repolist` | Android Library | Repository list screen — Smart/Dumb composables, `RepoListViewModel` |

---

## Convention Plugins

Build logic lives in `:build-logic:convention`. Each module declares one or more plugins rather than copy-pasting dependency blocks.

| Plugin ID | What it wires |
|---|---|
| `iamkamrul.android.application` | AGP application, Kotlin, core deps |
| `iamkamrul.android.application.compose` | Compose compiler + BOM for the app module |
| `iamkamrul.android.library` | AGP library, Kotlin |
| `iamkamrul.android.library.compose` | Compose compiler + BOM for library modules |
| `iamkamrul.android.feature.compose` | Library + Compose + Hilt + Navigation + Lifecycle + Serialization |
| `iamkamrul.android.hilt` | Hilt + KSP compiler |
| `iamkamrul.android.retrofit` | Retrofit + OkHttp + Gson + Coroutines |
| `iamkamrul.android.room` | Room + KSP compiler |
| `iamkamrul.jvm.library` | Pure JVM Kotlin module (no Android APIs) |

---

## Key Design Decisions

### Typed Error Handling

Rather than throwing exceptions or returning nullable types, every repository operation returns `Flow<Resource<T>>`:

```kotlin
sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val error: DataError) : Resource<Nothing>
}

sealed interface DataError {
    sealed interface Network : DataError {
        data object NoInternet : Network
        data object RequestTimeout : Network
        data class ServerError(val code: Int) : Network
        // ...
    }
}
```

Errors are exhaustively matched in the ViewModel — no silent failures.

### NetworkBoundResource

A generic cache-first fetch strategy. Emits cached data immediately, then fetches from the network and emits updated data (or a typed error):

```kotlin
fun <T> networkBoundResource(
    query: () -> Flow<T?>,
    fetch: suspend () -> T,
    saveFetchResult: suspend (T) -> Unit,
    shouldFetch: (T?) -> Boolean = { true }
): Flow<Resource<T>>
```

### Type-Safe Navigation

All navigation routes are `@Serializable` data objects — no string-based destinations:

```kotlin
@Serializable data object RepoListRoute
@Serializable data object ProfileRoute

// NavHost
composable<RepoListRoute> { RepoListScreenRoute(...) }
composable<ProfileRoute>  { ProfileScreenRoute(...)  }
```

### core:domain is Android-free

`:core:domain` is a **JVM library** module. It has no `android` block, no `Context`, no Android imports. This makes domain logic independently testable on the JVM without an emulator.

---

## Project Structure

```
.
├── app/
│   └── src/main/
│       ├── di/                  # AppModule
│       └── navigation/          # AppNavigation (NavHost)
├── build-logic/
│   └── convention/              # Custom Gradle convention plugins
├── common/                      # Shared JVM utilities
├── core/
│   ├── data/
│   │   ├── apiservice/          # Retrofit interface
│   │   ├── client/              # NetworkBoundResource, NetworkErrorParser, ConnectivityObserverImpl
│   │   ├── dto/                 # API response data classes
│   │   ├── mapper/              # DTO → Entity mappers
│   │   ├── module/              # Hilt modules (OkHttp, Retrofit, Repository, Connectivity)
│   │   └── repoimpl/            # Repository implementations
│   ├── designsystem/
│   │   ├── component/           # ScaffoldTopAppbar
│   │   └── theme/               # Color, Type, Shape, Theme
│   ├── di/
│   │   ├── module/              # CoroutineModule
│   │   └── qualifier/           # @IoDispatcher, @AppBaseUrl
│   ├── domain/
│   │   ├── connectivity/        # ConnectivityObserver interface
│   │   ├── entity/              # ProfileEntity, RepoItemEntity
│   │   ├── outcome/             # Resource<T>, DataError, ResourceExt
│   │   ├── repository/          # GithubRepository interface
│   │   └── usecase/             # BaseUseCase, ProfileUseCase, RepoListUseCase
│   └── ui/
│       ├── components/          # NetworkErrorMessage
│       └── error/               # DataError → string resource mapping
└── features/
    ├── profile/
    │   ├── ProfileNavigation.kt # NavGraphBuilder extension
    │   ├── ProfileScreen.kt     # Smart (Route) + Dumb composables
    │   └── ProfileViewModel.kt  # UiState + UiAction + ViewModel
    └── repolist/
        ├── RepoListNavigation.kt
        ├── RepoListScreen.kt
        └── RepoListViewModel.kt
```

---

## Getting Started

### Prerequisites

- Android Studio Meerkat or newer
- JDK 17+
- Android SDK with API 37

### Clone & Run

```bash
git clone https://github.com/kamrul3288/JetPackCompose-Modularization-MVVM-Clean-Architecture.git
cd JetPackCompose-Modularization-MVVM-Clean-Architecture
```

Open in Android Studio and run the `:app` configuration on a device or emulator running API 23+.

### Build Variants

| Variant | Base URL |
|---|---|
| `debug` | `https://api.github.com/` (set in `app/src/debug/BaseUrlModule.kt`) |
| `release` | Configured in `app/src/release/BaseUrlModule.kt` |

---

## License

```
Copyright 2024 Kamrul Hasan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
