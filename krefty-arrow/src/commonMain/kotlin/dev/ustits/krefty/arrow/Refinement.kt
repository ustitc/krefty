package dev.ustits.krefty.arrow

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.fold
import dev.ustits.krefty.core.Predicate

@DslMarker annotation class RefineDsl

@RefineDsl
fun <A, B, Error> refine(value: A, block: RefinementRaise<A, Error>.() -> B): Either<Error, B> {
    return fold({ block(RefinementRaise(value, this)) }, { Either.Left(it) }, { Either.Right(it) })
}

class RefinementRaise<A, Error>(val value: A, private val raise: Raise<Error>) : Raise<Error> by raise {

    @RefineDsl
    fun ensure(predicate: Predicate<A>, error: () -> Error): A {
        return if (predicate.isRefined(value)) value else raise(error())
    }
}
