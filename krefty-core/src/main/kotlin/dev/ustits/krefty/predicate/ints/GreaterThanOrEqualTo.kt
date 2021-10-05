package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Or

class GreaterThanOrEqualTo(value: Int) : Predicate<Int> by Or(GreaterThan(value), EqualTo(value))
