package dev.ustits.krefty.core

abstract class Refinery<T, R> {

    protected abstract fun refinement(value: T): Refinement<R>

    fun fromOrElse(value: T, default: R): R = fromOrElse(value) { default }

    fun fromOrElse(value: T, default: () -> R): R = refinement(value).getOrElse(default)

    fun fromOrNull(value: T): R? = refinement(value).getOrNull()

    fun fromOrThrow(value: T): R = refinement(value).getOrThrow()

    fun fromOrError(value: T): Result<R> = refinement(value).getOrError()

}

abstract class NullRefinery<T, R> : Refinery<T, R>() {

    fun from(value: T): R? = fromOrNull(value)

}

abstract class ThrowingRefinery<T, R> : Refinery<T, R>() {

    fun from(value: T): R = fromOrThrow(value)

}

abstract class ResultRefinery<T, R> : Refinery<T, R>() {

    fun from(value: T): Result<R> = fromOrError(value)

}
