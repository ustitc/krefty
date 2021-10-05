package dev.ustits.krefty.predicate.ints

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.forAll

class EqualToTest : StringSpec({

    "returns true if equal to number" {
        val predicate = EqualTo(10)
        predicate.isRefined(10) shouldBe true
    }

    "returns false if not equal to number" {
        val value = 10
        forAll(intNotEqualTo(value)) {
            val predicate = EqualTo(value)
            !predicate.isRefined(it)
        }
    }

})

private fun intNotEqualTo(value: Int): Arb<Int> {
    return Arb.int().filter { it != value }
}
