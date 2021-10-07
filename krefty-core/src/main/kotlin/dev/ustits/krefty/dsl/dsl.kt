package dev.ustits.krefty.dsl

import dev.ustits.krefty.core.EagerRefined
import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.core.Refined

infix fun <P : Predicate<T>, T> T.refineWith(predicate: P): Refined<P, T> {
    return EagerRefined(predicate, this)
}

infix fun <P : Predicate<T>, T> T.refineWithOrResult(predicate: P): Result<Refined<P, T>> {
    return kotlin.runCatching { EagerRefined(predicate, this) }
}

infix fun <P : Predicate<T>, T> T.refineWithOrNull(predicate: P): Refined<P, T>? {
    return refineWithOrResult(predicate).getOrNull()
}
