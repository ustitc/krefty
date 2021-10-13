package dev.ustits.krefty.core

@JvmInline
value class LazyRefined<P : Predicate<T>, T> private constructor(
    private val inner: Lazy<Refined<P, T>>
) : Refined<P, T> {

    constructor(
        predicate: P,
        value: T,
        onError: (T) -> Throwable = { RefinementException("Value $it doesn't match the predicate") }
    ) : this(
        lazy { EagerRefined(predicate, value, onError) }
    )

    override val unrefined: T
        get() = inner.value.unrefined
}
