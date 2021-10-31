package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate

class Even : Predicate<Int> {

    override fun isRefined(value: Int): Boolean {
        return value.mod(2) == 0
    }
}
