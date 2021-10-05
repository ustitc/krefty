package dev.ustits.krefty.predicate.logical

import dev.ustits.krefty.core.Predicate

class Or<P : Predicate<T>, T>(private val predicates: List<P>) : Predicate<T>  {

    @Suppress("SpreadOperator")
    constructor(head: P, vararg tail: P) : this(listOf(head, *tail))

    override fun isRefined(value: T): Boolean {
        return predicates.any { it.isRefined(value) }
    }
}
