package dev.ustits.krefty.core

import dev.ustits.krefty.predicate.string.Blank
import dev.ustits.krefty.predicate.string.Length
import dev.ustits.krefty.predicate.string.NotBlank
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe

class RefinementTest : StringSpec ({

    "returns value" {
        val refinement = refine(NotBlank(), "oil")
        refinement.getOrThrow() shouldBe "oil"
        refinement.getOrNull() shouldBe "oil"
        refinement.getOrElse { "gas" } shouldBe "oil"
        refinement.getOrError() shouldBeSuccess "oil"
    }

    "returns default value" {
        val refinement = refine(Blank(), "oil")
        refinement.getOrElse { "gas" } shouldBe "gas"
    }

    "returns null" {
        val refinement = refine(NotBlank(), "")
        refinement.getOrNull() shouldBe null
    }

    "returns error" {
        val refinement = refine(NotBlank(), "")
        refinement.getOrError() shouldBeFailure RefinementException("")
    }

    "throws exception" {
        val refinement = refine(NotBlank(), "")
        shouldThrow<RefinementException> {
            refinement.getOrThrow()
        }
    }
    
    "maps if can be refined" {
        val refinement = refine(NotBlank(), "oil").map { "gas" }
        refinement.getOrThrow() shouldBe "gas"
    }

    "doesn't map if can't be refined" {
        val refinement = refine(NotBlank(), "").map { "gas" }
        refinement.getOrNull() shouldBe null
    }

    "flatMaps if can be refined" {
        val refinement = refine(NotBlank(), "oil").flatMap {
            refine(Length(3), it)
        }
        refinement.getOrThrow() shouldBe "oil"
    }

    "doesn't flatMap if can't be refined" {
        val refinement = refine(NotBlank(), "").flatMap {
            refine(Length(0), it)
        }
        refinement.getOrNull() shouldBe null
    }

    "returns error if after flatMap can't be refined" {
        val refinement = refine(NotBlank(), "oil").flatMap {
            refine(Length(2), it)
        }
        refinement.getOrNull() shouldBe null
    }

    "keeps value if matches filter" {
        val refinement = refine("oil").filter {
            it.length > 1
        }
        refinement.getOrThrow() shouldBe "oil"
    }

    "doesn't keep value if not matches filter" {
        val refinement = refine("oil").filter {
            it.length > 10
        }
        refinement.getOrNull() shouldBe null
    }

    "keeps value if matches filter by predicate" {
        val refinement = refine("oil").filter(NotBlank())
        refinement.getOrThrow() shouldBe "oil"
    }

    "doesn't keep value if not matches filter by predicate" {
        val refinement = refine("oil").filter(Blank())
        refinement.getOrNull() shouldBe null
    }
})
