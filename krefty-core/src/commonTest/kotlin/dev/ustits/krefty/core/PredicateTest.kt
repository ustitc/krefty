package dev.ustits.krefty.core

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.forAll

class PredicateTest : StringSpec({

    "and constructs predicate returning logical AND of predicates" {
        forAll<Boolean, Boolean> { a, b ->
            val predicate = Predicate.Stub<Any>(a) and Predicate.Stub(b)
            predicate.isRefined(Any()) == a and b
        }
    }

    "or constructs predicate returning logical OR of predicates" {
        forAll<Boolean, Boolean> { a, b ->
            val predicate = Predicate.Stub<Any>(a) or Predicate.Stub(b)
            predicate.isRefined(Any()) == a or b
        }
    }

})
