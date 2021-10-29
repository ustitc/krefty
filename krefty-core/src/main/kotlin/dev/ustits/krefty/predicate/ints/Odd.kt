package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.dsl.not

class Odd : Predicate<Int> by !Even()
