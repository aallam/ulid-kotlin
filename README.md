# ULID for Kotlin

> Universally Unique Lexicographically Sortable Identifier

[![Maven Central](https://img.shields.io/maven-central/v/com.aallam.ulid/ulid-kotlin?color=blue&label=Download)](https://search.maven.org/artifact/com.aallam.ulid/ulid-kotlin)
[![License](https://img.shields.io/github/license/Aallam/ulid-kotlin?color=yellow)](LICENSE.md)
[![Kotlin](https://img.shields.io/badge/kotlin-1.7.0-blue.svg?logo=kotlin)](https://kotlinlang.org/docs/releases.html#release-details)
[![codecov](https://codecov.io/gh/Aallam/ulid-kotlin/branch/main/graph/badge.svg?token=E3KWyGWD9n)](https://codecov.io/gh/Aallam/ulid-kotlin)

Kotlin implementation of [ULID](https://github.com/ulid/spec#specification) with multiplatform support.

## Background

UUID can be suboptimal for many use-cases because:

- It isn't the most character efficient way of encoding 128 bits of randomness
- UUID v1/v2 is impractical in many environments, as it requires access to a unique, stable MAC address
- UUID v3/v5 requires a unique seed and produces randomly distributed IDs, which can cause fragmentation in many data
  structures
- UUID v4 provides no other information than randomness which can cause fragmentation in many data structures

Instead, herein is proposed ULID:

- 128-bit compatibility with UUID
- 1.21e+24 unique ULIDs per millisecond
- Lexicographically sortable!
- Canonically encoded as a 26 character string, as opposed to the 36 character UUID
- Uses Crockford's base32 for better efficiency and readability (5 bits per character)
- Case-insensitive
- No special characters (URL safe)
- Monotonic sort order (correctly detects and handles the same millisecond)

## Install

Add the Maven Central repository if it is not already there:

```groovy
repositories {
    mavenCentral()
}
```

Add a dependency to the dependencies block:

```groovy
dependencies {
    implementation "com.aallam.ulid:ulid-kotlin:$version"
}
```

### Multiplatform

In multiplatform projects, add a dependency to the `commonMain` source set dependencies.

## Usage

* Generating _ULID_ String:

```kotlin
ULID.randomULID()
```

* Generating `ULID` instance:

```kotlin
val ulid = ULID.nextULID()
val ulidString = ulid.toString()
```

* Generating `ULID` using `ULID.Factory`

```kotlin
val factory = ULID.Factory()
val ulid = factory.nextULID()
val ulidString = ulid.toString()
```

The default constructor is using default `kotlin.random.Random` but you can also use the `ULID.Factory(Random)` builder
function to use a different `Random` instance.

```kotlin
// generate a ULID String
val ulidString: String = ULID.randomULID()

// generate a ULID instance
val ulid: ULID = ULID.nextULID()

// generate the ByteArray for a ULID instance
val data: ByteArray = ulid.toBytes()

// generate a ULID from given ByteArray using 'fromBytes' function
val ulidFromBytes: ByteArray = ULID.fromBytes(data)

// generate a ULID from given String using 'parseULID' function
val ulidFromString: ULID = ULID.parseULID(ulidString)

// generate a ULID String from ULID instance
val ulidStringFromULID: ULID = ulid.toString()
```

### Monotonicity

[Specification](https://github.com/ulid/spec#monotonicity)

```kotlin
// generate ULID instance using a monotonic factory
val ulid: ULID = ULID.Monotonic.nextULID(previousULID)

// using a monotonic factory, generate a ULID instance or null in case of overflow
val ulidStrict: ULID? = ULID.Monotonic.nextULIDStrict(previousULID)
```

## Specification

Below is the current specification of ULID as implemented in this repository.

### Components

**Timestamp**

- 48 bits
- UNIX-time in milliseconds
- Won't run out of space till the year 10889 AD

**Entropy**

- 80 bits
- User defined entropy source.

### Encoding

[Crockford's Base32](http://www.crockford.com/wrmg/base32.html) is used as shown.
This alphabet excludes the letters I, L, O, and U to avoid confusion and abuse.

```
0123456789ABCDEFGHJKMNPQRSTVWXYZ
```

### Binary Layout and Byte Order

The components are encoded as 16 octets. Each component is encoded with the Most Significant Byte first (network byte
order).

```
0                   1                   2                   3
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                      32_bit_uint_time_high                    |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     16_bit_uint_time_low      |       16_bit_uint_random      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                       32_bit_uint_random                      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                       32_bit_uint_random                      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
```

### String Representation

```
 01AN4Z07BY      79KA1307SR9X4MV3
|----------|    |----------------|
 Timestamp           Entropy
  10 chars           16 chars
   48bits             80bits
   base32             base32
```

## Prior Art

- [huxi/sulky](https://github.com/huxi/sulky)

## License

ULID for Kotlin is an open-sourced software licensed under the [MIT license](LICENSE.md).
