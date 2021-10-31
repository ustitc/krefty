package dev.ustits.krefty.predicate.string

import dev.ustits.krefty.core.Predicate

class Empty : Predicate<String> {

    override fun isRefined(value: String): Boolean {
        return value.isEmpty()
    }
}
