package dev.ustits.krefty.core

interface Refined<P : Predicate<T>, T> {

    @Deprecated("Use getOrThrow() instead")
    val unrefined: T

    fun getOrElse(block: (T) -> T): T

    fun getOrThrow(): T

    fun getOrNull(): T?

    fun getOrError(): Result<T>

}

fun <T, P : Predicate<T>> refine(predicate: P, value: T): Refined<P, T> {
    return LazyRefined(predicate, value)
}
