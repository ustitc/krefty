package dev.ustits.krefty.core

import dev.ustits.krefty.predicate.logical.All
import dev.ustits.krefty.predicate.logical.Not
import dev.ustits.krefty.predicate.logical.Some

@Deprecated(message = "For removal")
fun interface Predicate<in T> {

    fun isRefined(value: T): Boolean

    class Stub<T>(private val toReturn: () -> Boolean) : Predicate<T> {

        constructor(result: Boolean) : this({ result })

        override fun isRefined(value: T): Boolean {
            return toReturn()
        }
    }

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
