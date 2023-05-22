package dev.ustits.krefty.arrow

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import dev.ustits.krefty.core.Refinement
import dev.ustits.krefty.core.Refinery

object RefinementError

fun <A> Refinement<A>.getOrEither(): Either<RefinementError, A> {
    return getOrNull()?.right() ?: RefinementError.left()
}

fun <A, B> Refinery<A, B>.ofOrEither(value: A): Either<RefinementError, B> = refinement(value).getOrEither()

interface EitherRefinery<A, B> : Refinery<A, B> {

    fun from(value: A): Either<RefinementError, B> = ofOrEither(value)

}
