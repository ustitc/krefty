package dev.ustits.krefty.arrow

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import dev.ustits.krefty.core.Refinement
import dev.ustits.krefty.core.Refinery
import dev.ustits.krefty.core.refine

object RefinementError

fun <A> Refinement<A>.getOrEither(): Either<RefinementError, A> {
    return getOrNull()?.right() ?: RefinementError.left()
}

abstract class EitherRefinery<A, B> : Refinery<A, B>() {

    fun from(value: A): Either<RefinementError, B> = refine(value).refine().getOrEither()

}
