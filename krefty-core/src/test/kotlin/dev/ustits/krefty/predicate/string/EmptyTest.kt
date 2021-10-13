package dev.ustits.krefty.predicate.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll

class EmptyTest : StringSpec({

    "returns true on empty value" {
        val predicate = Empty()
        predicate.isRefined("") shouldBe true
    }

    "returns false on non empty values" {
        forAll(Arb.string(minSize = 1)) {
            !Empty().isRefined(it)
        }
    }

})
