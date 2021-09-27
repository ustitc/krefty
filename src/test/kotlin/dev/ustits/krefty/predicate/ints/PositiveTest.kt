package dev.ustits.krefty.predicate.ints

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInts
import io.kotest.property.arbitrary.positiveInts
import io.kotest.property.forAll

class PositiveTest : StringSpec({

    "returns true on positive numbers" {
        forAll(Arb.positiveInts()) {
            Positive().isRefined(it)
        }
    }

    "returns false on negative numbers" {
        forAll(Arb.negativeInts()) {
            !Positive().isRefined(it)
        }
    }

    "returns false on zero" {
        Positive().isRefined(0) shouldBe false
    }

})
