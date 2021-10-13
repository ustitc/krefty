package dev.ustits.krefty.predicate.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll

class NotEmptyTest : StringSpec({

    "returns false on empty value" {
        val predicate = NotEmpty()
        predicate.isRefined("") shouldBe false
    }

    "returns true on non empty values" {
        forAll(Arb.string(minSize = 1)) {
            NotEmpty().isRefined(it)
        }
    }

})
