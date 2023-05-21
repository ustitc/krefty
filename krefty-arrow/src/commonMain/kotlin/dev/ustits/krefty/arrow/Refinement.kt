package dev.ustits.krefty.arrow

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import dev.ustits.krefty.core.Refinement

object RefinementError

fun <A> Refinement<A>.getOrEither(): Either<RefinementError, A> {
    return getOrNull()?.right() ?: RefinementError.left()
}
