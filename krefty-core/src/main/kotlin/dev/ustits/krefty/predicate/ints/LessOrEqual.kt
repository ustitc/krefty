package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.dsl.or
import dev.ustits.krefty.predicate.Equal

class LessOrEqual(value: Int) : Predicate<Int> by Less(value) or Equal(value)
