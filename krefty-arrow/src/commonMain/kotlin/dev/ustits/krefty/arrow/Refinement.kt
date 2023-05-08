package dev.ustits.krefty.arrow

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.RaiseDSL
import arrow.core.raise.fold
import dev.ustits.krefty.core.Predicate

fun <A, B, Error> refine(value: A, block: RefinementRaise<A, Error>.(A) -> B): Either<Error, B> {
    return fold({ block(RefinementRaise(this), value) }, { Either.Left(it) }, { Either.Right(it) })
}

class RefinementRaise<A, Error>(private val raise: Raise<Error>) : Raise<Error> by raise {

    @RaiseDSL
    fun refine(predicate: Predicate<A>, value: A, raise: () -> Error): A {
        return if (predicate.isRefined(value)) value else raise(raise())
    }
}
