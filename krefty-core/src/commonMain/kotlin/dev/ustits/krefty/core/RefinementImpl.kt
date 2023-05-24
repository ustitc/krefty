package dev.ustits.krefty.core

internal class RefinementImpl<T> internal constructor(
    private val value: T,
    private val predicate: (T) -> Boolean
) : Refinement<T> {

    override fun getOrElse(block: () -> T): T = getOrNull() ?: block()

    override fun getOrThrow(): T = if (isRefined()) value else throw RefinementException(value)

    override fun getOrNull(): T? = if (isRefined()) value else null

    override fun getOrError(): Result<T> =
        if (isRefined()) {
            Result.success(value)
        } else {
            Result.failure(RefinementException(value))
        }

    override fun <R> map(block: (T) -> R): Refinement<R> =
        if (isRefined()) {
            RefinementImpl(block(value)) { true }
        } else {
            ErrorRefinement(RefinementException(value))
        }

    override fun <R> flatMap(block: (T) -> Refinement<R>): Refinement<R> =
        if (isRefined()) {
            block(value)
        } else {
            ErrorRefinement(RefinementException(value))
        }

    override fun filter(block: (T) -> Boolean): Refinement<T> =
        RefinementImpl(value) {
            predicate.invoke(value) && block.invoke(value)
        }

    override fun filter(refinery: Refinery<T, *>): Refinement<T> =
        RefinementImpl(value) {
            predicate.invoke(value) && refinery.refinement(value).isRefined()
        }

    override fun filter(predicate: Predicate<T>): Refinement<T> =
        RefinementImpl(value) {
            predicate.isRefined(it) && this.predicate.invoke(it)
        }

    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> = suspendRefine(value) {
        predicate.invoke(value) && block.invoke(value)
    }

    override fun isRefined(): Boolean = predicate.invoke(value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RefinementImpl<*>

        if (value != other.value) return false
        return predicate == other.predicate
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}

private class ErrorRefinement<T>(private val exception: RefinementException) : Refinement<T> {
    override fun getOrElse(block: () -> T): T = block()
    override fun getOrThrow(): T = throw exception
    override fun getOrNull(): T? = null
    override fun getOrError(): Result<T> = Result.failure(exception)
    override fun <R> map(block: (T) -> R): Refinement<R> = ErrorRefinement(exception)
    override fun <R> flatMap(block: (T) -> Refinement<R>): Refinement<R> = ErrorRefinement(exception)
    override fun filter(block: (T) -> Boolean): Refinement<T> = this
    override fun filter(refinery: Refinery<T, *>): Refinement<T> = ErrorRefinement(exception)
    override fun filter(predicate: Predicate<T>): Refinement<T> = this
    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> = ErrorSuspendRefinement(exception)
    override fun isRefined(): Boolean = false
}