package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate

class Negative : Predicate<Int> by Less(0)
