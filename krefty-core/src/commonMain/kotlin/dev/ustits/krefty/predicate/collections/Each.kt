package dev.ustits.krefty.predicate.collections

import dev.ustits.krefty.core.Predicate

class Each<in C : Collection<T>, T>(private val elementPredicate: Predicate<T>) : Predicate<C> {

    override fun isRefined(value: C): Boolean {
        return value.all { elementPredicate.isRefined(it) }
    }
}
