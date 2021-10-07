package dev.ustits.krefty.predicate.string

import dev.ustits.krefty.core.Predicate

class Length(private val length: Int) : Predicate<String> {

    override fun isRefined(value: String): Boolean {
        return value.length == length
    }
}
