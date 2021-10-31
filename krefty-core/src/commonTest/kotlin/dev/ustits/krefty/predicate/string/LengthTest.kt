package dev.ustits.krefty.predicate.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll

class LengthTest : StringSpec({

    "returns true if string is of specified length" {
        val size = 23
        forAll(Arb.string(size)) {
            Length(size).isRefined(it)
        }
    }

    "returns false if string is smaller" {
        val size = 23
        forAll(Arb.string(maxSize = size - 1)) {
            !Length(size).isRefined(it)
        }
    }

    "returns false if string is larger" {
        val size = 23
        forAll(Arb.string(minSize = size + 1)) {
            !Length(size).isRefined(it)
        }
    }

})
