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

To refine a type use `refined` function with a predicate:

```kotlin
val name = "Krefty" refined NotBlank()
```

Function will ensure that the value `"Krefty"` satisfies the predicate `NotBlank`. If not it will cause an error. 

Call `unrefined` to get the value back:

```kotlin
name.unrefined // "Krefty"
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
val userID = 443812 refined UserID()

class Percent : Predicate<Int> by And(GreaterOrEqual(0), LessOrEqual(100))
val percent = 45 refined Percent()
```
