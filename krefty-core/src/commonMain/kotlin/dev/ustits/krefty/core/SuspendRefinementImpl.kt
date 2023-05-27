package dev.ustits.krefty.core

import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

internal class SuspendRefinementImpl<T>(
    private val value: T,
    private val predicate: suspend (T) -> Boolean
) : SuspendRefinement<T> {

    override suspend fun getOrElse(block: suspend () -> T): T = getOrNull() ?: block()

    override suspend fun getOrThrow(): T = getOrNull() ?: throw RefinementException(value)

    override suspend fun getOrNull(): T? = value.takeIf { predicate(it) }

    override suspend fun getOrError(): Result<T> = getOrNull()
        ?.let { success(it) } ?: failure(RefinementException(value))

    override suspend fun isRefined(): Boolean = predicate(value)

    override fun <R> map(block: (T) -> R): SuspendRefinement<R> {
        return WrappingSuspendRefinement {
            if (predicate(value)) {
                suspendRefine(block(value)) { true }
            } else {
                ErrorSuspendRefinement(RefinementException(value))
            }
        }
    }

    override fun <R> flatMap(block: (T) -> SuspendRefinement<R>): SuspendRefinement<R> {
        return WrappingSuspendRefinement {
            if (predicate(value)) {
                block(value)
            } else {
                ErrorSuspendRefinement(RefinementException(value))
            }
        }
    }

    override fun <R> flatMap(refinery: Refinery<T, R>): SuspendRefinement<R> = flatMap {
        refinery.refinement(value).suspendFilter { true }
    }

    override fun filter(block: (T) -> Boolean): SuspendRefinement<T> {
        return SuspendRefinementImpl(value) {
            this.predicate(value) && block(value)
        }
    }

    override fun filter(refinery: Refinery<T, *>): SuspendRefinement<T> {
        return SuspendRefinementImpl(value) {
            this.predicate(value) && refinery.refinement(value).isRefined()
        }
    }

    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> {
        return SuspendRefinementImpl(value) {
            this.predicate(value) && block(value)
        }
    }
}

private class WrappingSuspendRefinement<T>(
    private val source: suspend () -> SuspendRefinement<T>
) : SuspendRefinement<T> {
    override suspend fun getOrElse(block: suspend () -> T): T = source.invoke().getOrElse(block)
    override suspend fun getOrThrow(): T = source.invoke().getOrThrow()
    override suspend fun getOrNull(): T? = source.invoke().getOrNull()
    override suspend fun getOrError(): Result<T> = source.invoke().getOrError()
    override suspend fun isRefined(): Boolean = source.invoke().isRefined()

    override fun <R> map(block: (T) -> R): SuspendRefinement<R> =
        WrappingSuspendRefinement {
            source().map(block)
        }

    override fun <R> flatMap(block: (T) -> SuspendRefinement<R>): SuspendRefinement<R> =
        WrappingSuspendRefinement {
            source().flatMap(block)
        }

    override fun <R> flatMap(refinery: Refinery<T, R>): SuspendRefinement<R> =
        WrappingSuspendRefinement {
            source().flatMap(refinery)
        }

    override fun filter(block: (T) -> Boolean): SuspendRefinement<T> =
        WrappingSuspendRefinement {
            source().filter(block)
        }

    override fun filter(refinery: Refinery<T, *>): SuspendRefinement<T> =
        WrappingSuspendRefinement {
            source().filter(refinery)
        }

    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> =
        WrappingSuspendRefinement {
            source().suspendFilter(block)
        }
}

internal class ErrorSuspendRefinement<T>(private val exception: RefinementException) : SuspendRefinement<T> {
    override suspend fun getOrElse(block: suspend () -> T): T = block()
    override suspend fun getOrThrow(): T = throw exception
    override suspend fun getOrNull(): T? = null
    override suspend fun getOrError(): Result<T> = failure(exception)
    override suspend fun isRefined(): Boolean = false
    override fun <R> map(block: (T) -> R): SuspendRefinement<R> = ErrorSuspendRefinement(exception)
    override fun <R> flatMap(block: (T) -> SuspendRefinement<R>): SuspendRefinement<R> =
        ErrorSuspendRefinement(exception)

    override fun <R> flatMap(refinery: Refinery<T, R>): SuspendRefinement<R> = ErrorSuspendRefinement(exception)
    override fun filter(block: (T) -> Boolean): SuspendRefinement<T> = ErrorSuspendRefinement(exception)
    override fun filter(refinery: Refinery<T, *>): SuspendRefinement<T> = ErrorSuspendRefinement(exception)
    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> = ErrorSuspendRefinement(exception)
}
