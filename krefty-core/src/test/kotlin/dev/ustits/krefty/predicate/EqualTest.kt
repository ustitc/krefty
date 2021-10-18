package dev.ustits.krefty.predicate

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.forAll

class EqualTest : StringSpec({

    "returns true if equal" {
        val predicate = Equal(10)
        predicate.isRefined(10) shouldBe true
    }

    "returns false if not equal" {
        val value = 10
        forAll(intNotEqualTo(value)) {
            val predicate = Equal(value)
            !predicate.isRefined(it)
        }
    }

})
