package dev.ustits.krefty.predicate.maps

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll

class EmptyTest : StringSpec({

    "returns true if map is empty" {
        val predicate = Empty<Map<String, String>>()
        predicate.isRefined(emptyMap()) shouldBe true
    }

    "returns false if map is not empty" {
        forAll(Arb.map(Arb.string(), Arb.string(), minSize = 1, includeEmpty = false)) {
            val predicate = Empty<Map<String, String>>()
            !predicate.isRefined(it)
        }
    }

})
