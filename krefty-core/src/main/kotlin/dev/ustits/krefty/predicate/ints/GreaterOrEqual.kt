package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.dsl.or

class GreaterOrEqual(value: Int) : Predicate<Int> by Greater(value) or Equal(value)
