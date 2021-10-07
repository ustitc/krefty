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

To refine a type use `refineWith` function with a predicate:

```kotlin
val refined = "Krefty" refineWith NotBlank()
```

Function will ensure that the value `"Krefty"` satisfies the predicate `NotBlank`. If not it will cause an error. 

Call `unrefined` to get the value back:

```kotlin
refined.unrefined // "Krefty"
```

A newly created object can be used to construct new types, for example, 
by passing it in the constructor:

```kotlin
class NotBlankString private constructor(private val value: String) {

    constructor(refined: Refined<NotBlank, String>) : this(refined.unrefined)

}

val notBlank = NotBlankString(refined)
```

Construct new predicates using delegation:

```kotlin
class UserID : Predicate<Int> by Positive()
val userID = 443812 refineWith UserID()

class Percent : Predicate<Int> by And(GreaterThanOrEqualTo(0), LesserThanOrEqualTo(100))
val percent = 45 refineWith Percent()
```

New types can also be created by delegation. To construct a type implement `Refined` interface 
and reuse `LazyRefined` implementation:

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
