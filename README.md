![Krefty](docs/krefty.png)

---

[![CI](https://github.com/ustits/krefty/actions/workflows/build.yml/badge.svg)](https://github.com/ustits/krefty/actions/workflows/build.yml)
[![Licence](https://img.shields.io/github/license/ustits/krefty)](https://github.com/ustits/krefty/blob/main/LICENSE)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.ustits.krefty/krefty-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.ustits.krefty/krefty-core)

Krefty is a tool for constructing concrete (refined) types specific for your domain. It uses refinement type theory,
i.e. types wrapping a predicate and a value which satisfies it. 
For DDD users refined types can be viewed as an alternative to value objects or [whole objects](http://c2.com/ppr/checks.html#1). 
Inspired by implementations in [haskell](https://github.com/nikita-volkov/refined)
and [scala](https://github.com/fthomas/refined).

Also check out [arrow-exact](https://github.com/arrow-kt/arrow-exact) and [values4k](https://github.com/fork-handles/forkhandles/tree/trunk/values4k) which solve the same problem. 

## Getting started

```
implementation("dev.ustits.krefty:krefty-core:<latest_version>")
```

## Usage

Use `refine` and pass a desired predicate. It will return a `Refinement` type which holds a value if it 
matches a predicate or an exception if not:

```kotlin
val name = refine(NotBlank(), "Krefty") 
name.getOrThrow()       // "Krefty"
name.isRefined()        // true

val version = refine(NotBlank(), "")
version.getOrThrow()    // throws RefinementException
version.isRefined()     // false
```

`Refinement` can be used in the same way as Collections, Either and other monads:

```kotlin
refine("Krefty")
    .filter(NotBlank())
    .filter { it.length > 3 }
    .map { NotBlankString(it) }
    .flatMap { refine(UserNamePredicate(), it) }
```

### Predicates

Krefty is shipped with predefined `Predicate`s but it strongly encouraged to make domain specific ones. 
New predicates can be made by implementing `Predicate` or by using delegation:

```kotlin
class UserID : Predicate<Int> {
}
// or
class UserID : Predicate<Int> by Positive()

val userID = refine(UserID(), 443812) 
```

Combine predicates using `and`, `or` functions or by `All` and `Some` classes:

```kotlin
class Percent : Predicate<Int> by GreaterOrEqual(0) and LessOrEqual(100)
// or
class Percent : Predicate<Int> by All(GreaterOrEqual(0), LessOrEqual(100))

val percent = 45 refine Percent()
```

Invert predicates with `!` function or `Not` class:

```kotlin
class NotPercent : Predicate<Int> by !Percent()
// or
class NotPercent : Predicate<Int> by Not(Percent())
```
