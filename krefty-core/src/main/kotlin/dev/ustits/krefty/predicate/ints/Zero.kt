package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.Equal

class Zero : Predicate<Int> by Equal(0)
