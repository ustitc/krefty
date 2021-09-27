package dev.ustits.krefty.predicate.ints

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInts
import io.kotest.property.arbitrary.positiveInts
import io.kotest.property.forAll

class NegativeTest : StringSpec({

    "returns false on positive numbers" {
        forAll(Arb.positiveInts()) {
            !Negative().isRefined(it)
        }
    }

    "returns true on negative numbers" {
        forAll(Arb.negativeInts()) {
            Negative().isRefined(it)
        }
    }

    "returns false on zero" {
        Negative().isRefined(0) shouldBe false
    }

})
