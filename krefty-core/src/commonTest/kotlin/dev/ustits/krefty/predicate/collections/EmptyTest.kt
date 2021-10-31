package dev.ustits.krefty.predicate.collections

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll

class EmptyTest : StringSpec({

    "returns true if list is empty" {
        val predicate = Empty<List<String>>()
        predicate.isRefined(emptyList()) shouldBe true
    }

    "returns false if list is not empty" {
        forAll(Arb.list(Arb.string(), 1..100)) {
            val predicate = Empty<List<String>>()
            !predicate.isRefined(it)
        }
    }

})
