package dev.ustits.krefty.core

internal class StaticRefined<P : Predicate<T>, T>(private val value: T) : Refined<P, T> {
    override fun unrefined(): T {
        return value
    }
}
