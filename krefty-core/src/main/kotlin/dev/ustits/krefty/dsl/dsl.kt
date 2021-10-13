package dev.ustits.krefty.dsl

import dev.ustits.krefty.core.EagerRefined
import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.core.Refined

infix fun <P : Predicate<T>, T> T.refined(predicate: P): Refined<P, T> {
    return EagerRefined(predicate, this)
}

infix fun <P : Predicate<T>, T> T.refinedOrError(predicate: P): Result<Refined<P, T>> {
    return kotlin.runCatching { EagerRefined(predicate, this) }
}

infix fun <P : Predicate<T>, T> T.refinedOrNull(predicate: P): Refined<P, T>? {
    return refinedOrError(predicate).getOrNull()
}
