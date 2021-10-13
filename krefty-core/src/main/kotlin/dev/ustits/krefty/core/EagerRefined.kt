package dev.ustits.krefty.core

internal class EagerRefined<P : Predicate<T>, T> private constructor(private val value: T) : Refined<P, T> {

    constructor(
        predicate: P,
        value: T,
        onError: (T) -> Throwable = { RefinementException("Value $it doesn't match the predicate") }
    ) : this(
        if (predicate.isRefined(value)) {
            value
        } else {
            throw onError.invoke(value)
        }
    )

    override val unrefined: T
        get() = value
}
