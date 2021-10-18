package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.Greater

class Positive : Predicate<Int> by Greater(0)
