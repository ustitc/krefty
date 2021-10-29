package dev.ustits.krefty.dsl

import dev.ustits.krefty.core.EagerRefined
import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.core.Refined
import dev.ustits.krefty.predicate.logical.All
import dev.ustits.krefty.predicate.logical.Not
import dev.ustits.krefty.predicate.logical.Some

infix fun <P : Predicate<T>, T> T.refined(predicate: P): Refined<P, T> {
    return EagerRefined(predicate, this)
}

infix fun <P : Predicate<T>, T> T.refinedOrError(predicate: P): Result<Refined<P, T>> {
    return kotlin.runCatching { EagerRefined(predicate, this) }
}

infix fun <P : Predicate<T>, T> T.refinedOrNull(predicate: P): Refined<P, T>? {
    return refinedOrError(predicate).getOrNull()
}

infix fun <T> Predicate<T>.and(predicate: Predicate<T>): Predicate<T> {
    return All(listOf(this, predicate))
}

infix fun <T> Predicate<T>.or(predicate: Predicate<T>): Predicate<T> {
    return Some(listOf(this, predicate))
}

operator fun <T> Predicate<T>.not(): Not<Predicate<T>, T> {
    return Not(this)
}
