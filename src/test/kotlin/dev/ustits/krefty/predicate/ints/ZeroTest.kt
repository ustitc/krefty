package dev.ustits.krefty.predicate.ints

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInts
import io.kotest.property.arbitrary.positiveInts
import io.kotest.property.forAll

class ZeroTest : StringSpec({

    "returns false on positive numbers" {
        forAll(Arb.positiveInts()) {
            !Zero().isRefined(it)
        }
    }

    "returns false on negative numbers" {
        forAll(Arb.negativeInts()) {
            !Zero().isRefined(it)
        }
    }

    "returns true on zero" {
        Zero().isRefined(0) shouldBe true
    }

})
