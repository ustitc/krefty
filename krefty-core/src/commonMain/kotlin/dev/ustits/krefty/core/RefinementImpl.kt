package dev.ustits.krefty.core

import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

internal data class RefinementImpl<T> internal constructor(
    private val value: T,
    private val predicate: (T) -> Boolean
) : Refinement<T> {

    override fun getOrElse(block: () -> T): T = getOrNull() ?: block()

    override fun getOrThrow(): T = getOrNull() ?: throw RefinementException(value)

    override fun getOrNull(): T? = value.takeIf(predicate)

    override fun getOrError(): Result<T> = getOrNull()
        ?.let { success(it) } ?: failure(RefinementException(value))

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

    override fun <R> flatMap(refinery: Refinery<T, R>): Refinement<R> = flatMap {
        refinery.refinement(value)
    }

    override fun filter(block: (T) -> Boolean): Refinement<T> =
        RefinementImpl(value) {
            predicate(value) && block(value)
        }

    override fun filter(refinery: Refinery<T, *>): Refinement<T> =
        RefinementImpl(value) {
            predicate(value) && refinery.refinement(value).isRefined()
        }

    override fun filter(predicate: Predicate<T>): Refinement<T> =
        RefinementImpl(value) {
            predicate.isRefined(it) && this.predicate(it)
        }

    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> = suspendRefine(value) {
        predicate(value) && block(value)
    }

    override fun isRefined(): Boolean = predicate(value)
}

private class ErrorRefinement<T>(private val exception: RefinementException) : Refinement<T> {
    override fun getOrElse(block: () -> T): T = block()
    override fun getOrThrow(): T = throw exception
    override fun getOrNull(): T? = null
    override fun getOrError(): Result<T> = failure(exception)
    override fun <R> map(block: (T) -> R): Refinement<R> = ErrorRefinement(exception)
    override fun <R> flatMap(block: (T) -> Refinement<R>): Refinement<R> = ErrorRefinement(exception)
    override fun <R> flatMap(refinery: Refinery<T, R>): Refinement<R> = ErrorRefinement(exception)
    override fun filter(block: (T) -> Boolean): Refinement<T> = this
    override fun filter(refinery: Refinery<T, *>): Refinement<T> = ErrorRefinement(exception)
    override fun filter(predicate: Predicate<T>): Refinement<T> = this
    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> = ErrorSuspendRefinement(exception)
    override fun isRefined(): Boolean = false
}