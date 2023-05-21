package dev.ustits.krefty.core

interface SuspendRefinement<T> {

    suspend fun getOrElse(block: suspend () -> T): T

    suspend fun getOrThrow(): T

    suspend fun getOrNull(): T?

    suspend fun getOrError(): Result<T>

    suspend fun isRefined(): Boolean

    fun <R> map(block: (T) -> R): SuspendRefinement<R>

    fun <R> flatMap(block: (T) -> SuspendRefinement<R>): SuspendRefinement<R>

    fun filter(block: (T) -> Boolean): SuspendRefinement<T>

    fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T>

}

fun <T> suspendRefine(value: T, predicate: suspend (T) -> Boolean): SuspendRefinement<T> {
    return SuspendRefinementImpl(value, predicate)
}
