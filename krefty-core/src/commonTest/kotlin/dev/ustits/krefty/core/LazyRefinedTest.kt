package dev.ustits.krefty.core

import dev.ustits.krefty.dsl.refine
import dev.ustits.krefty.predicate.string.Blank
import dev.ustits.krefty.predicate.string.NotBlank
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe

class LazyRefinedTest : StringSpec ({

    "returns value" {
        val refined = "oil" refine NotBlank()
        refined.getOrThrow() shouldBe "oil"
        refined.getOrNull() shouldBe "oil"
        refined.getOrElse { "gas" } shouldBe "oil"
        refined.getOrError() shouldBeSuccess "oil"
    }

    "returns default value" {
        val refined = "oil" refine Blank()
        refined.getOrElse { "$it & gas" } shouldBe "oil & gas"
    }

    "returns null" {
        val refined = "" refine NotBlank()
        refined.getOrNull() shouldBe null
    }

    "returns error" {
        val refined = "" refine NotBlank()
        refined.getOrError() shouldBeFailure RefinementException("")
    }

    "throws exception" {
        val refined = "" refine NotBlank()
        shouldThrow<RefinementException> {
            refined.getOrThrow()
        }
    }
})
