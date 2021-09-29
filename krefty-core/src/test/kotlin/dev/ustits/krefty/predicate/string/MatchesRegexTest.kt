package dev.ustits.krefty.predicate.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.exhaustive.exhaustive
import io.kotest.property.forAll

class MatchesRegexTest : StringSpec({

    "returns true if matches regex" {
        forAll(listOf("it is 10 am", "it is\t12 pm", "it\nis\n1\nam").exhaustive()) {
            MatchesRegex("it\\s+is\\s+\\d{1,2}\\s+(am|pm)").isRefined(it)
        }
    }

    "returns false if doesn't match regex" {
        forAll(listOf("It is 10 am", "it is\t121 pm", "it\nis\n-1\nam").exhaustive()) {
            !MatchesRegex("it\\s+is\\s+\\d{1,2}\\s+(am|pm)").isRefined(it)
        }
    }

})
