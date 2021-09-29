package dev.ustits.krefty.predicate.logical

import dev.ustits.krefty.core.Predicate
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.forAll

class AndTest : StringSpec({

    "gives logical AND of two predicated" {
        forAll<Boolean, Boolean> { a, b ->
            val predicate = And(Predicate.Stub(a), Predicate.Stub<Int>(b))
            predicate.isRefined(10) == a and b
        }
    }

})
