package dev.ustits.krefty.predicate

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.forAll

class LessTest : StringSpec({

    "returns true if less than number" {
        val value = 10
        forAll(Arb.int(Int.MIN_VALUE until value)) {
            val predicate = Less(value)
            predicate.isRefined(it)
        }
    }

    "returns false if greater than number" {
        val value = 10
        forAll(Arb.int((value + 1)..Int.MAX_VALUE)) {
            val predicate = Less(value)
            !predicate.isRefined(it)
        }
    }

    "returns false if equal to number" {
        val predicate = Less(10)
        predicate.isRefined(10) shouldBe false
    }

})
