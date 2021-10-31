package dev.ustits.krefty.predicate.logical

import dev.ustits.krefty.core.Predicate

class Not<P : Predicate<T>, T>(private val inner: P) : Predicate<T> {

    override fun isRefined(value: T): Boolean {
        return inner.isRefined(value).not()
    }
}
