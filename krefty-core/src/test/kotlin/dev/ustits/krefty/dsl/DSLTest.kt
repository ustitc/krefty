package dev.ustits.krefty.dsl

import dev.ustits.krefty.core.Predicate
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

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

})
