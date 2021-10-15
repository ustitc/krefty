package dev.ustits.krefty.dsl

import dev.ustits.krefty.core.Predicate
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.forAll

class DSLTest : StringSpec({

    "refined returns object if can refine" {
        shouldNotThrowAny {
            "test" refined Predicate.Stub(true)
        }
    }

    "refined throws exception if can't refine" {
        shouldThrowAny {
            "test" refined Predicate.Stub(false)
        }
    }

    "refinedOrError returns ok result if can refine" {
        val result = "test" refinedOrError Predicate.Stub(true)
        result.isSuccess shouldBe true
    }

    "refinedOrError returns failed result if can refine" {
        val result = "test" refinedOrError Predicate.Stub(false)
        result.isFailure shouldBe true
    }

    "refinedOrNull returns object if can refine" {
        val result = "test" refinedOrNull Predicate.Stub(true)
        result shouldNotBe null
    }

    "refinedOrNull returns null if can't refine" {
        val result = "test" refinedOrNull Predicate.Stub(false)
        result shouldBe null
    }

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
