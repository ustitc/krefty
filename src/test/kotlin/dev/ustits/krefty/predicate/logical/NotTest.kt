package dev.ustits.krefty.predicate.logical

import dev.ustits.krefty.core.Predicate
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.forAll

class NotTest : StringSpec({

    "gives inverted result" {
        forAll<Boolean> {
            val predicate = Not(Predicate.Stub<Int>(it))
            predicate.isRefined(10) != it
        }
    }

})
