package dev.ustits.krefty.predicate.logical

import dev.ustits.krefty.core.Predicate
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.forAll

class OrTest : StringSpec({

    "gives logical OR of two predicated" {
        forAll<Boolean, Boolean> { a, b ->
            val predicate = Or(Predicate.Stub(a), Predicate.Stub<Int>(b))
            predicate.isRefined(10) == a or b
        }
    }

    "returns true if any predicate is true" {
        val predicates = listOf(
            Predicate.Stub<String>(false),
            Predicate.Stub(false),
            Predicate.Stub(true)
        )
        val or = Or(predicates)
        or.isRefined("") shouldBe true
    }

})
