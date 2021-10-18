package dev.ustits.krefty.core

fun interface Predicate<in T> {

    fun isRefined(value: T): Boolean

    class Stub<T>(private val toReturn: () -> Boolean) : Predicate<T> {

        constructor(result: Boolean) : this({ result })

        override fun isRefined(value: T): Boolean {
            return toReturn()
        }
    }

}
