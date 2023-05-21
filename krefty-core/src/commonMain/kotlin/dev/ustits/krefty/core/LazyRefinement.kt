package dev.ustits.krefty.core

internal class LazyRefinement<T> internal constructor(
    private val value: T,
    private val predicate: (T) -> Boolean
) : Refinement<T> {

    override fun getOrElse(block: () -> T): T {
        return getOrNull() ?: block()
    }

    override fun getOrThrow(): T {
        if (!isRefined()) {
            throw RefinementException(value)
        }
        return value
    }

    override fun getOrNull(): T? {
        return if (isRefined()) {
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

    override fun <R> map(block: (T) -> R): Refinement<R> {
        return flatMap { Holder(block(value)) }
    }

    override fun <R> flatMap(block: (T) -> Refinement<R>): Refinement<R> {
        return if (isRefined()) {
            block(value)
        } else {
            Error(RefinementException(value))
        }
    }

    override fun filter(block: (T) -> Boolean): Refinement<T> {
        return if (isRefined() && block(value)) {
            this
        } else {
            Error(RefinementException(value))
        }
    }

    override fun filter(predicate: Predicate<T>): Refinement<T> {
        return LazyRefinement(value) {
            predicate.isRefined(it) && this.predicate.invoke(it)
        }
    }

    override fun isRefined(): Boolean = predicate.invoke(value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as LazyRefinement<*>

        if (value != other.value) return false
        return predicate == other.predicate
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    private class Holder<T>(private val value: T) : Refinement<T> {
        override fun getOrElse(block: () -> T): T = value
        override fun getOrThrow(): T = value
        override fun getOrNull(): T? = value
        override fun getOrError(): Result<T> = Result.success(value)
        override fun <R> map(block: (T) -> R): Refinement<R> = Holder(block(value))
        override fun <R> flatMap(block: (T) -> Refinement<R>): Refinement<R> = block(value)
        override fun filter(block: (T) -> Boolean): Refinement<T> = if (block(value)) {
            this
        } else {
            Error(RefinementException(value))
        }

        override fun filter(predicate: Predicate<T>): Refinement<T> = LazyRefinement(value) { predicate.isRefined(it) }
        override fun isRefined(): Boolean = true
    }

    private class Error<T>(private val exception: RefinementException) : Refinement<T> {
        override fun getOrElse(block: () -> T): T = block()
        override fun getOrThrow(): T = throw exception
        override fun getOrNull(): T? = null
        override fun getOrError(): Result<T> = Result.failure(exception)
        override fun <R> map(block: (T) -> R): Refinement<R> = Error(exception)
        override fun <R> flatMap(block: (T) -> Refinement<R>): Refinement<R> = Error(exception)
        override fun filter(block: (T) -> Boolean): Refinement<T> = this
        override fun filter(predicate: Predicate<T>): Refinement<T> = this
        override fun isRefined(): Boolean = false
    }
}
