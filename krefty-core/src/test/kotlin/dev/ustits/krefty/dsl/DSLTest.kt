package dev.ustits.krefty.dsl

import dev.ustits.krefty.core.Predicate
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class DSLTest : StringSpec({

    "refineWith returns object if can refine" {
        shouldNotThrowAny {
            "test" refineWith Predicate.Stub(true)
        }
    }

    "refineWith throws exception if can't refine" {
        shouldThrowAny {
            "test" refineWith Predicate.Stub(false)
        }
    }

    "refineWithOrResult returns ok result if can refine" {
        val result = "test" refineWithOrResult Predicate.Stub(true)
        result.isSuccess shouldBe true
    }

    "refineWithOrResult returns failed result if can refine" {
        val result = "test" refineWithOrResult Predicate.Stub(false)
        result.isFailure shouldBe true
    }

    "refineWithOrNull returns object if can refine" {
        val result = "test" refineWithOrNull Predicate.Stub(true)
        result shouldNotBe null
    }

    "refineWithOrNull returns null if can't refine" {
        val result = "test" refineWithOrNull Predicate.Stub(false)
        result shouldBe null
    }

})
