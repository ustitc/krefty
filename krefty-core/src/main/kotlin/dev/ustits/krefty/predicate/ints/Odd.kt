package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Not

class Odd : Predicate<Int> by Not(Even())
