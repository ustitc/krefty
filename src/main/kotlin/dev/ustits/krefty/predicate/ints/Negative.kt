package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.And
import dev.ustits.krefty.predicate.logical.Not

class Negative : Predicate<Int> by And(Not(Positive()), Not(Zero()))
