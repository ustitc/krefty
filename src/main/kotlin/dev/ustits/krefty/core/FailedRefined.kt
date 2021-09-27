package dev.ustits.krefty.core

@JvmInline
internal value class FailedRefined<P : Predicate<T>, T>(private val throwable: Throwable) : Refined<P, T> {

    override val unrefined: T
        get() = throw throwable
}
