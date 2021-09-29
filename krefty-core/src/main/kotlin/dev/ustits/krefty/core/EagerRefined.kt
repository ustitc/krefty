package dev.ustits.krefty.core

class EagerRefined<P : Predicate<T>, T> private constructor(private val value: T) : Refined<P, T> {

    constructor(
        predicate: P,
        value: T,
        onError: (T) -> Throwable = { RefinementException("Value $it doesn't match the predicate") }
    ) : this(LazyRefined(predicate, value, onError))

    constructor(lazy: LazyRefined<P, T>) : this(lazy.unrefined)

    override val unrefined: T
        get() = value
}
