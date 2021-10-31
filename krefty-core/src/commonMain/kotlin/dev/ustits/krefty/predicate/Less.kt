package dev.ustits.krefty.predicate

import dev.ustits.krefty.core.Predicate

class Less<in T>(private val toCompare: Comparable<T>): Predicate<T> {

    override fun isRefined(value: T): Boolean {
        return toCompare > value
    }
}
