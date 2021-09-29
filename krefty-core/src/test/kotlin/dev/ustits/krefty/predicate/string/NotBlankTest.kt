package dev.ustits.krefty.predicate.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.forAll

class NotBlankTest : StringSpec({

    "returns false on blank values" {
        forAll(Arb.stringPattern("\\s+")) {
            !NotBlank().isRefined(it)
        }
    }

    "returns true on non blank values" {
        forAll(Arb.stringPattern("\\w+")) {
            NotBlank().isRefined(it)
        }
    }

})
