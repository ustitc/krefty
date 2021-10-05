# Krefty - refined types for Kotlin

[![CI](https://github.com/ustits/krefty/actions/workflows/build.yml/badge.svg)](https://github.com/ustits/krefty/actions/workflows/build.yml)
[![Licence](https://img.shields.io/github/license/ustits/krefty)](https://github.com/ustits/krefty/blob/main/LICENSE)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.ustits.krefty/krefty-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.ustits.krefty/krefty-core)

Krefty is a tool for constructing concrete (refined) types specific for your domain. It uses refinement type theory,
i.e. types wrapping a predicate and a value which satisfies it. Inspired by implementations
in [haskell](https://github.com/nikita-volkov/refined)
and [scala](https://github.com/fthomas/refined).

## Getting started

```
implementation("dev.ustits.krefty:krefty-core:<latest_version>")
```

## Usage

To construct a type implement `Refined` interface and reuse `LazyRefined` implementation:

```kotlin 
class PositiveInt(value: Int) : Refined<Positive, Int> by LazyRefined(Positive(), value)

val positive = PositiveInt(10)
positive.unrefined

val negative = PositiveInt(-10)
negative.unrefined // throws exception
```

If you need to fail immediately use `EagerRefined`:

```kotlin
class PositiveInt(value: Int) : Refined<Positive, Int> by EagerRefined(Positive(), value)

val negative = PositiveInt(-10) // throws exception
negative.unrefined
```

If you need something complex define your own predicate:

```kotlin
class PercentPredicate : Predicate<Int> by And(GreaterThanOrEqualTo(0), LesserThanOrEqualTo(100))

class Percent(value: Int) : Refined<PercentPredicate, Int> by LazyRefined(PercentPredicate(), value) 
```

