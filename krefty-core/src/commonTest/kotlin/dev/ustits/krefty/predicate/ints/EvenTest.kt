package dev.ustits.krefty.predicate.ints

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.forAll

class EvenTest : StringSpec({

    "returns true on even numbers" {
        forAll(Arb.int().filter { it.mod(2) == 0 }) {
            Even().isRefined(it)
        }
    }

    "returns false on odd numbers" {
        forAll(Arb.int().filter { it.mod(2) != 0 }) {
            !Even().isRefined(it)
        }
    }

})
