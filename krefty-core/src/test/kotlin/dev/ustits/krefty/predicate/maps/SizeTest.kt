package dev.ustits.krefty.predicate.maps

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.map
import io.kotest.property.forAll

class SizeTest : StringSpec({

    "returns true if list size is of specified size" {
        forAll(Arb.map(Arb.int(), Arb.int(), minSize = 2)) {
            val predicate = Size<Map<Int, Int>>(it.size)
            predicate.isRefined(it)
        }
    }

    "returns false if list is smaller" {
        forAll(Arb.map(Arb.int(), Arb.int(), minSize = 10)) {
            val predicate = Size<Map<Int, Int>>(it.size / 2)
            !predicate.isRefined(it)
        }
    }

    "returns false if list is larger" {
        forAll(Arb.map(Arb.int(), Arb.int(), minSize = 10)) {
            val predicate = Size<Map<Int, Int>>(it.size * 2)
            !predicate.isRefined(it)
        }
    }

})
