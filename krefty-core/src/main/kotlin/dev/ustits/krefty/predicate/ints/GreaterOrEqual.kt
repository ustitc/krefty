package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Or

class GreaterOrEqual(value: Int) : Predicate<Int> by Or(Greater(value), Equal(value))
