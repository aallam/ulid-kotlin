# 1.6.0

### Added
- `ULID.StatefulMonotonic`: thread-safe, stateful monotonic ULID generator (#50)
- jvm: `ULID.toUUID()` and `ULID.fromUUID()` extension functions (#49)

### Changed
- Optimize entropy generation to use exactly 80 bits of randomness (#50)
- Update Kover to `0.9.6`, Spotless to `6.25.0`, Dokka to `2.1.0` (#50)

### Fixed
- Use unsigned comparison in `ULID.compareTo` (#49)

# 1.5.0

### Added
- wasmJS target (#46) (thanks @walterbrebels)

### Changed
- Update Kotlin to `2.2.0` (#46) (thanks @walterbrebels)

### Removed
- Dependency on `kotlinx.datetime` (#47)

# 1.4.0
> Published 12 Jul 2025

### Added
- jvm: `ULID` implements `kotlinx.serialization.Serializable` (#39) (thanks @Lazalatin)

### Changed
- Update Kotlin to `2.0.20`

### Removed
- `watchosX86` target removed

# 1.3.0
> Published 26 Jul 2023

### Added
- jvm: `ULID` implements `java.io.Serializable` (#34)

### Changed
- Update Kotlin to `1.9.0`

# 1.2.1
> Published 29 Jan 2023

### Added
- Darwin simulator and x86 targets

# 1.2.0
> Published 30 Dec 2022

### Added
- Kotlin/Native: add `ios`, `watchos` and `tvos` targets

### Changed
- Update Kotlin to `1.8.0`

### Removed
- Kotlin/JS: `Legacy` compiler


# 1.1.0
> Published 14 Jul 2022

### Added
- Kotlin/JS: support `IR` and `Legacy` compilers

### Changed
- Update Kotlin to `1.7.10`


# 1.0.0
> Published 10 Jun 2022

### Added
* Enable compatibility with non-hierarchical projects

### Changed
* Package to `ulid`
* Artifact ID to `ulid-kotlin`
* Update Kotlin to `1.7.0`


# 0.1.0
> Published 31 May 2022

### Added
* Implementation for JVM, JS and Native targets
* Binary implementation support
* Monotonicity support
