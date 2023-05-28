# Krefty

[![CI](https://github.com/ustits/krefty/actions/workflows/build.yml/badge.svg)](https://github.com/ustits/krefty/actions/workflows/build.yml)
[![Licence](https://img.shields.io/github/license/ustits/krefty)](https://github.com/ustits/krefty/blob/main/LICENSE)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.ustits.krefty/krefty-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.ustits.krefty/krefty-core)

Krefty is a Kotlin library that empowers the creation of domain-specific types while addressing the Primitive Obsession anti-pattern. 
It provides a robust framework for constructing types inspired by the Refinement Type Theory, where types are composed of a predicate and a value that satisfies it.

Krefty is particularly useful for Domain-Driven Design (DDD) users, where refined types can be viewed as a viable 
alternative to Value Objects. 
Inspired by implementations in [Haskell](https://github.com/nikita-volkov/refined)
and [Scala](https://github.com/fthomas/refined).

Also check out [Arrow-Exact](https://github.com/arrow-kt/arrow-exact) and [Values4k](https://github.com/fork-handles/forkhandles/tree/trunk/values4k) which solve the same problem. 

## Getting started 🚀

Add Krefty to your dependencies:

``` kotlin
implementation("dev.ustits.krefty:krefty-core:<latest_version>")
```

For snapshot versions

``` kotlin
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

implementation("dev.ustits.krefty:krefty-core:<latest_version>-SNAPSHOT")
```

## Usage

### Refinery 🏭

Let's consider an example where we need a type for describing names. We must ensure that the name is not empty and that 
we have a specific type for it:

```kotlin
@JvmInline
value class Name private constructor(private val value: String) {

    companion object : Refinery<String, Name>() {
        override fun Refinement<String>.refine() = filter { it.isNotBlank() }.map { Name(it) }
    }
}
```

We define a `Name` class that holds a `String` value.
The `Refinery` serves as a medium to fine-tune the conversion from String to Name. This is done by implementing 
the `refine()` function, which applies filters to check if the string is non-empty. 
If the string satisfies this condition, it is then transformed into a `Name` instance.

You now have the ability to generate Name instances, for example by using the `fromOrThrow` method:

``` kotlin
val grog = Name.fromOrThrow("Grog") // returns a Name instance "Grog"
val void = Name.fromOrThrow("")     // throws RefinementException
```

For a simplified version of `from`, you can apply specific `Refinery` implementations:

- `NullRefinery`
- `ThrowingRefinery`
- `ResultRefinery`

For instance, with `ResultRefinery`, the usage would look like this:

``` kotlin
companion object : ResultRefinery<String, Name>()

Name.from("Scanlan")  // returns Result.success
```

### Refinement 🛢️

`Refinement` is a core concept in the Krefty. You can think of it as a container (monad) for a value and a predicate.
If the value matches the predicate, it holds that value, otherwise, it holds an error.

Like Collections, Either, and other monadic types, `Refinement` provides several operations that can be used to 
manipulate and transform the refined type:

```kotlin
refinement
    .filter { it.isNotBlank() }
    .map { NotBlankString(it) }
    .flatMap { refine(it, this::isName) }
```

`Refinery` itself can also be used as a transformation:

```kotlin
refinement
    .filter(NotBlankString)
    .flatMap(Name)
```

You can also use `Refinement` separately from `Refinery`, for example by using `refine` function:

```kotlin
val name = refine("Krefty") { it.isNotBlank() } 
name.getOrThrow()       // "Krefty"
name.isRefined()        // true

val version = refine("") { it.isNotBlank() }
version.getOrThrow()    // throws RefinementException
version.isRefined()     // false
```

For refinements that involve side effects, the `suspendRefine` function can be used:

```kotlin
suspendRefine("94926946-2e51-4b14-a9bd-2ce9ad02b29b") {
    service.existsById(it)
}

class Service {
    suspend fun existsById(id: String): Boolean
}
```

### Arrow

Krefty can be used with `Either` type from [Arrow](https://github.com/arrow-kt/arrow). In order to use it add `krefty-arrow` to your dependencies:

``` kotlin
implementation("dev.ustits.krefty:krefty-arrow:<latest_version>")
```

Then you can use `EitherRefinery` to get results as an `Either`:

```kotlin
Name.from("Keyleth") // Either<RefinementError, Name>
```
