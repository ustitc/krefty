package dev.ustits.krefty.predicate.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.forAll

class BlankTest : StringSpec({

    "returns true on blank values" {
        forAll(Arb.stringPattern("\\s+")) {
            Blank().isRefined(it)
        }
    }

    "returns false on non blank values" {
        forAll(Arb.stringPattern("\\w+")) {
            !Blank().isRefined(it)
        }
    }
})
