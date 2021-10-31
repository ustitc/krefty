package dev.ustits.krefty.predicate.logical

import dev.ustits.krefty.core.Predicate
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.forAll

class AllTest : StringSpec({

    "gives logical AND of two predicated" {
        forAll<Boolean, Boolean> { a, b ->
            val predicate = All(Predicate.Stub(a), Predicate.Stub<Int>(b))
            predicate.isRefined(10) == a and b
        }
    }

    "returns false if any predicate is false" {
        val predicates = listOf(
            Predicate.Stub<String>(true),
            Predicate.Stub(false),
            Predicate.Stub(true)
        )
        val all = All(predicates)
        all.isRefined("") shouldBe false
    }

})
