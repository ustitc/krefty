package dev.ustits.krefty.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe

class SuspendRefinementTest : StringSpec({

    "returns value" {
        val refinement = suspendRefine("oil") { it.isNotBlank() }
        refinement.getOrThrow() shouldBe "oil"
        refinement.getOrNull() shouldBe "oil"
        refinement.getOrElse { "gas" } shouldBe "oil"
        refinement.getOrError() shouldBeSuccess "oil"
    }

    "returns default value" {
        val refinement = suspendRefine("oil") { it.isBlank() }
        refinement.getOrElse { "gas" } shouldBe "gas"
    }

    "returns null" {
        val refinement = suspendRefine("") { it.isNotBlank() }
        refinement.getOrNull() shouldBe null
    }

    "returns error" {
        val refinement = suspendRefine("") { it.isNotBlank() }
        refinement.getOrError() shouldBeFailure RefinementException("")
    }

    "throws exception" {
        val refinement = suspendRefine("") { it.isNotBlank() }
        shouldThrow<RefinementException> {
            refinement.getOrThrow()
        }
    }

    "maps if can be refined" {
        val refinement = suspendRefine("oil") { it.isNotBlank() }.map { "gas" }
        refinement.getOrThrow() shouldBe "gas"
    }

    "doesn't map if can't be refined" {
        val refinement = suspendRefine("") { it.isNotBlank() }.map { "gas" }
        refinement.getOrNull() shouldBe null
    }

    "flatMaps if can be refined" {
        val refinement = suspendRefine("oil") { it.isNotBlank() }
            .flatMap { str ->
                suspendRefine(str) { it.length == 3 }
            }
        refinement.getOrThrow() shouldBe "oil"
    }

    "doesn't flatMap if can't be refined" {
        val refinement = suspendRefine("") { it.isNotBlank() }
            .flatMap { str ->
                suspendRefine(str) { it.isEmpty() }
            }
        refinement.getOrNull() shouldBe null
    }

    "returns error if after flatMap can't be refined" {
        val refinement = suspendRefine("oil") { it.isNotBlank() }
            .flatMap { str ->
                suspendRefine(str) { it.length == 2 }
            }
        refinement.getOrNull() shouldBe null
    }

    "keeps value if matches filter" {
        val refinement = suspendRefine("oil") { it.isNotBlank() }
            .filter {
                it.length > 1
            }
        refinement.getOrThrow() shouldBe "oil"
    }

    "doesn't keep value if not matches filter" {
        val refinement = suspendRefine("oil") { it.isNotBlank() }
            .filter {
                it.length > 10
            }
        refinement.getOrNull() shouldBe null
    }

    "is refined if matches predicate" {
        val refinement = suspendRefine("oil") { it.isNotBlank() }
        refinement.isRefined() shouldBe true
    }

    "is not refined if doesn't matche predicate" {
        val refinement = suspendRefine("") { it.isNotBlank() }
        refinement.isRefined() shouldBe false
    }

    "is refined by Refinery" {
        val refinement = suspendRefine("test") { true }.filter(NotBlankString)
        refinement.isRefined() shouldBe true
    }

    "is not refined by Refinery" {
        val refinement = suspendRefine("") { true }.filter(NotBlankString)
        refinement.isRefined() shouldBe false
    }

    "flatMaps by Refinery" {
        val refinement = suspendRefine("test") { true }.flatMap(NotBlankString)
        refinement.getOrThrow() shouldBe NotBlankString("test")
    }

    "flatMaps not by Refinery if not matching the predicate" {
        val refinement = suspendRefine("") { true }.flatMap(NotBlankString)
        refinement.isRefined() shouldBe false
    }
})
