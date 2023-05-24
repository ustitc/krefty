package dev.ustits.krefty.core

internal class SuspendRefinementImpl<T>(
    private val value: T,
    private val predicate: suspend (T) -> Boolean
) : SuspendRefinement<T> {

    override suspend fun getOrElse(block: suspend () -> T): T = getOrNull() ?: block()

    override suspend fun getOrThrow(): T = getOrNull() ?: throw RefinementException(value)

    override suspend fun getOrNull(): T? = if (predicate.invoke(value)) value else null

    override suspend fun getOrError(): Result<T> =
        if (predicate.invoke(value))
            Result.success(value)
        else
            Result.failure(RefinementException(value))

    override suspend fun isRefined(): Boolean = predicate.invoke(value)

    override fun <R> map(block: (T) -> R): SuspendRefinement<R> {
        return WrappingSuspendRefinement {
            if (predicate.invoke(value)) {
                suspendRefine(block(value)) { true }
            } else {
                ErrorSuspendRefinement(RefinementException(value))
            }
        }
    }

    override fun <R> flatMap(block: (T) -> SuspendRefinement<R>): SuspendRefinement<R> {
        return WrappingSuspendRefinement {
            if (predicate.invoke(value)) {
                block(value)
            } else {
                ErrorSuspendRefinement(RefinementException(value))
            }
        }
    }

    override fun filter(block: (T) -> Boolean): SuspendRefinement<T> {
        return SuspendRefinementImpl(value) {
            this.predicate.invoke(value) && block.invoke(value)
        }
    }

    override fun filter(refinery: Refinery<T, *>): SuspendRefinement<T> {
        return SuspendRefinementImpl(value) {
            this.predicate.invoke(value) && refinery.refinement(value).isRefined()
        }
    }

    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> {
        return SuspendRefinementImpl(value) {
            this.predicate.invoke(value) && block.invoke(value)
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
            source.invoke().map(block)
        }

    override fun <R> flatMap(block: (T) -> SuspendRefinement<R>): SuspendRefinement<R> =
        WrappingSuspendRefinement {
            source.invoke().flatMap(block)
        }

    override fun filter(block: (T) -> Boolean): SuspendRefinement<T> =
        WrappingSuspendRefinement {
            source.invoke().filter(block)
        }

    override fun filter(refinery: Refinery<T, *>): SuspendRefinement<T> =
        WrappingSuspendRefinement {
            source.invoke().filter(refinery)
        }

    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> =
        WrappingSuspendRefinement {
            source.invoke().suspendFilter(block)
        }
}

internal class ErrorSuspendRefinement<T>(private val exception: RefinementException) : SuspendRefinement<T> {
    override suspend fun getOrElse(block: suspend () -> T): T = block()
    override suspend fun getOrThrow(): T = throw exception
    override suspend fun getOrNull(): T? = null
    override suspend fun getOrError(): Result<T> = Result.failure(exception)
    override suspend fun isRefined(): Boolean = false
    override fun <R> map(block: (T) -> R): SuspendRefinement<R> = ErrorSuspendRefinement(exception)
    override fun <R> flatMap(block: (T) -> SuspendRefinement<R>): SuspendRefinement<R> =
        ErrorSuspendRefinement(exception)

    override fun filter(block: (T) -> Boolean): SuspendRefinement<T> = ErrorSuspendRefinement(exception)
    override fun filter(refinery: Refinery<T, *>): SuspendRefinement<T> = ErrorSuspendRefinement(exception)
    override fun suspendFilter(block: suspend (T) -> Boolean): SuspendRefinement<T> = ErrorSuspendRefinement(exception)
}
