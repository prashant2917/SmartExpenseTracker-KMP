# Copilot instructions for SmartExpenseTracker KMP

## Project overview
This repository is a Kotlin Multiplatform project targeting Android, iOS, Desktop (JVM), and a Ktor backend server. Follow the existing structure and keep responsibilities cleanly separated by module and platform layer.

## Module boundaries
- `core/`: shared business logic, domain models, repositories, use cases, validation, and cross-platform utilities. Prefer putting logic here when it is platform-independent.
- `app/shared/`: shared UI and app-level shared code that can compile for multiple clients. Keep platform-agnostic Compose or shared logic here.
- `app/androidApp/`, `app/desktopApp/`, `app/iosApp/`: platform entry points and thin UI bootstrap code only.
- `server/`: backend code, Ktor routing, API endpoints, persistence integration, and server-side business logic.

## Architecture rules
- Keep domain logic in `core` and avoid leaking Android/iOS/JVM-specific APIs into shared/common code.
- Use `commonMain` for code shared by all targets. Use platform-specific source sets only for actual platform integration (`androidMain`, `iosMain`, `jvmMain`).
- Prefer `expect`/`actual` declarations only at system boundaries such as platform detection, storage, notifications, or file access.
- Do not add platform-specific dependencies to shared code unless they are abstracted behind an interface or expect/actual contract.
- Keep UI code and business logic separate. The shared app layer should coordinate state and domain logic, not own unrelated infrastructure details.
- Keep server-side code isolated from client modules. `server` should not depend on app UI or shared presentation layers.

## Package and naming conventions
- Use package names under `com.swarajya.smartexpensetracker` with feature-based subpackages, for example:
  - `com.swarajya.smartexpensetracker.feature.expense`
  - `com.swarajya.smartexpensetracker.data.repository`
  - `com.swarajya.smartexpensetracker.ui.theme`
- Use clear, domain-driven names instead of generic names like `Util`, `Helper`, or `Manager` unless the responsibility is truly generic.
- Prefer descriptive file names that match the primary type or feature in that file.
- Keep one primary responsibility per file/class; split large classes into feature-specific components.

## Kotlin coding standards
- Prefer Kotlin idioms over Java-style patterns: `when`, `if` expressions, `data class`, `sealed class`, `enum class`, extension functions, and collection utilities.
- Favor immutability: use `val` by default, avoid mutable shared state unless necessary, and prefer `List`, `Set`, and `Map` transformations over imperative loops when readable.
- Use null-safe APIs and avoid unsafe casts (`as!`) or `!!` unless there is a strong, justified reason.
- Keep functions small and side-effect-free when possible. A function should do one clear thing.
- Prefer explicit return types and typed APIs over `Any`/`String`-heavy abstractions.
- Use `sealed class` or `Result`-style error handling for domain outcomes instead of throwing application exceptions for expected business errors.
- Use coroutines and structured concurrency properly for async work; do not block threads in shared code.
- When writing Compose UI code, keep composables small, readable, and state-driven, and avoid business logic inside composables.

## Testing expectations
- Add tests alongside the feature or module where the code lives.
- Prefer common tests in `commonTest` for shared logic, and platform-specific tests in the corresponding source set for platform behaviors.
- Cover domain logic, validation, and state transformations with unit tests before adding broader UI tests.
- Test edge cases and error states, not only happy paths.

## Dependency and code organization guidance
- Keep dependencies directed inward: `app` -> `core`, `server` -> `core` or specific backend modules, but not vice versa.
- Prefer interfaces or abstractions for platform access, repository boundaries, and external services.
- Use feature modules or package-level separation when a feature grows large; avoid monolithic files.
- Keep configuration and build logic in Gradle files, not embedded into business code.

## Implementation quality standards
- Match the style already present in this project: clear naming, clean package structure, and readable Kotlin code.
- Do not introduce unnecessary abstractions or generic frameworks just for architecture purity.
- Preserve multiplatform compatibility. If a change affects shared logic, check the cross-platform implications before finalizing it.
- Prefer minimal, maintainable changes that fit the current project design.

## When generating code
- Generate code consistent with the current package layout and feature organization.
- If new business features are added, create the corresponding files under the appropriate layer (`core` or `app/shared`) rather than placing everything in a single file.
- If platform-specific code is required, keep the implementation in the matching source set and expose a shared API behind a common contract.
- For server APIs, prefer clean request/response models and small, testable handlers.
- Add every `dp` and `sp` value as a named constant in `DimensionConstants` and `FontSizeConstants`.
- Use the constant naming pattern `DIMENSIONS_FOUR_DP` for `4.dp` and `FONT_SIZE_TEN_SP` for `10.sp`.
- Keep the names uppercase, use the numeric value in words, and append the unit suffix (`DP` or `SP`) in the final constant name.
