package dev.ustits.krefty.predicate.maps

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll

class NotEmptyTest : StringSpec({

    "returns false if map is empty" {
        val predicate = NotEmpty<Map<String, String>>()
        predicate.isRefined(emptyMap()) shouldBe false
    }

    "returns true if map is not empty" {
        forAll(Arb.map(Arb.string(), Arb.string(), minSize = 1)) {
            val predicate = NotEmpty<Map<String, String>>()
            predicate.isRefined(it)
        }
    }

})
