package dev.ustits.krefty.core

internal class LazyRefined<P : Predicate<T>, T> internal constructor(
    private val predicate: P,
    private val value: T
) : Refined<P, T> {

    override val unrefined: T
        get() = value

    override fun getOrElse(block: (T) -> T): T {
        return getOrNull() ?: block(value)
    }

    override fun getOrThrow(): T {
        if (!predicate.isRefined(value)) {
            throw RefinementException(value)
        }
        return value
    }

    override fun getOrNull(): T? {
        return if (predicate.isRefined(value)) {
            value
        } else {
            null
        }
    }

    override fun getOrError(): Result<T> {
        return if (getOrNull() == null) {
            Result.failure(RefinementException(value))
        } else {
            Result.success(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as LazyRefined<*, *>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

}
