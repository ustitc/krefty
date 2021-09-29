package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate

class Positive : Predicate<Int> {
    override fun isRefined(value: Int): Boolean {
        return value > 0
    }
}
