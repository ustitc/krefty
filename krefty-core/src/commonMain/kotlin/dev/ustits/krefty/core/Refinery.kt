package dev.ustits.krefty.core

interface Refinery<T, R> {

    fun refinement(value: T): Refinement<R>

    fun fromOrElse(value: T, default: R): R = fromOrElse(value) { default }

    fun fromOrElse(value: T, default: () -> R): R = refinement(value).getOrElse(default)

    fun fromOrNull(value: T): R? = refinement(value).getOrNull()

    fun fromOrThrow(value: T): R = refinement(value).getOrThrow()

    fun fromOrError(value: T): Result<R>? = refinement(value).getOrError()

}

interface NullRefinery<T, R> : Refinery<T, R> {

    fun from(value: T): R? = fromOrNull(value)

}

interface ThrowingRefinery<T, R> : Refinery<T, R> {

    fun from(value: T): R = fromOrThrow(value)

}

interface ResultRefinery<T, R> : Refinery<T, R> {

    fun from(value: T): Result<R>? = fromOrError(value)

}
