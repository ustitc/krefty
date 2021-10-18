package dev.ustits.krefty.predicate

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.forAll

class NotEqualTest : StringSpec({

    "returns false if equal" {
        val predicate = NotEqual(10)
        predicate.isRefined(10) shouldBe false
    }

    "returns true if not equal" {
        val value = 10
        forAll(intNotEqualTo(value)) {
            val predicate = NotEqual(value)
            predicate.isRefined(it)
        }
    }

})
