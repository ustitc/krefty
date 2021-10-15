package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.dsl.or

class LessOrEqual(value: Int) : Predicate<Int> by Less(value) or Equal(value)
