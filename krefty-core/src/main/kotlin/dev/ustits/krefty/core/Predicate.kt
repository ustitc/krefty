package dev.ustits.krefty.core

interface Predicate<in T> {

    fun isRefined(value: T): Boolean

    class Stub<T>(private val result: Boolean) : Predicate<T> {
        override fun isRefined(value: T): Boolean {
            return result
        }
    }

}
