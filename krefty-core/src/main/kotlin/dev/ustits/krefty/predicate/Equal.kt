package dev.ustits.krefty.predicate

import dev.ustits.krefty.core.Predicate

class Equal<T>(private val toCompare: T) : Predicate<T> {

    override fun isRefined(value: T): Boolean {
        return toCompare == value
    }
}
