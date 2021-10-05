package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Or

class LesserThanOrEqualTo(value: Int) : Predicate<Int> by Or(LesserThan(value), EqualTo(value))
