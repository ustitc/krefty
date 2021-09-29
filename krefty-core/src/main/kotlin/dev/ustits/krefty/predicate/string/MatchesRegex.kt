package dev.ustits.krefty.predicate.string

import dev.ustits.krefty.core.Predicate

class MatchesRegex(private val regex: Regex) : Predicate<String> {

    constructor(pattern: String) : this(pattern.toRegex())

    override fun isRefined(value: String): Boolean {
        return regex.matches(value)
    }
}
