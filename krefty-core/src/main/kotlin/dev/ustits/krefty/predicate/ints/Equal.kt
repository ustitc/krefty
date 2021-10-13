package dev.ustits.krefty.predicate.ints

import dev.ustits.krefty.core.Predicate

class Equal(private val number: Int) : Predicate<Int> {

    override fun isRefined(value: Int): Boolean {
        return number == value
    }
}
