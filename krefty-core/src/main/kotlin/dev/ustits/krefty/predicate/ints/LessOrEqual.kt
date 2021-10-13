package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Or

class LessOrEqual(value: Int) : Predicate<Int> by Or(Less(value), Equal(value))
