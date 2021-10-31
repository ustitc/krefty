package dev.ustits.krefty.predicate.string

import dev.ustits.krefty.core.Predicate

class Blank : Predicate<String> {

    override fun isRefined(value: String): Boolean {
        return value.isBlank()
    }
}
