package dev.ustits.krefty.core

@Deprecated(message = "Will be replaced by Refinement")
interface Refined<P : Predicate<T>, T> {

    @Deprecated("Use getOrThrow() instead")
    val unrefined: T

    fun getOrElse(block: (T) -> T): T

    fun getOrThrow(): T

    fun getOrNull(): T?

    fun getOrError(): Result<T>

}

interface Refinement<T> {

    fun getOrElse(block: () -> T): T

    fun getOrThrow(): T

    fun getOrNull(): T?

    fun getOrError(): Result<T>

    fun <R> map(block: (T) -> R): Refinement<R>

    fun <R> flatMap(block: (T) -> Refinement<R>): Refinement<R>

    fun filter(block: (T) -> Boolean): Refinement<T>

    fun filter(predicate: Predicate<T>): Refinement<T>

    fun isRefined(): Boolean

}

fun <T> refine(value: T): Refinement<T> {
    return LazyRefinement(Predicate.Stub(true), value)
}

fun <T, P : Predicate<T>> refine(predicate: P, value: T): Refinement<T> {
    return LazyRefinement(predicate, value)
}
