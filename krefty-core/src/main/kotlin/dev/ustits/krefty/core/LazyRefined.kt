package dev.ustits.krefty.core

@JvmInline
value class LazyRefined<P : Predicate<T>, T> private constructor(private val inner: Refined<P, T>) : Refined<P, T> {

    constructor(
        predicate: P,
        value: T,
        onError: (T) -> Throwable = { RefinementException("Value $it doesn't match the predicate") }
    ) : this(
        if (predicate.isRefined(value)) {
            StaticRefined(value)
        } else {
            FailedRefined(onError.invoke(value))
        }
    )

    override val unrefined: T
        get() = inner.unrefined
}
