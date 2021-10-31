package dev.ustits.krefty.predicate.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInts
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.forAll

class OnlyArabicNumeralsTest : StringSpec({

    "returns true if all chars in string are arabic numerals" {
        forAll(Arb.stringPattern("[0-9]+")) {
            OnlyArabicNumerals().isRefined(it)
        }
    }

    "returns false if any chars in string is not arabic numeral" {
        forAll(Arb.stringPattern("[^0-9]+[0-9]+")) {
            !OnlyArabicNumerals().isRefined(it)
        }
    }

    "returns false if string is a negative number" {
        forAll(Arb.negativeInts()) {
            !OnlyArabicNumerals().isRefined(it.toString())
        }
    }

    "returns false if string is blank" {
        forAll(Arb.stringPattern("\\s+")) {
            !OnlyArabicNumerals().isRefined(it)
        }
    }
})
