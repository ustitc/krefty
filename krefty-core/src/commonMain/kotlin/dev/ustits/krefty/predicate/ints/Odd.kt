package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.core.not

class Odd : Predicate<Int> by !Even()
