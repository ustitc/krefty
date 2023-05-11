package dev.ustits.krefty.arrow

import arrow.core.raise.ensure
import dev.ustits.krefty.predicate.string.NotBlank
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import kotlin.jvm.JvmInline

class RefinementTest : StringSpec({

    "returns right if matches condition" {
        val result = refine("Krefty") {
            ensure(value.isNotBlank()) { BlankString }
            NotBlankString(value)
        }
        result shouldBeRight NotBlankString("Krefty")
    }

    "returns left if doesn't match condition" {
        val result = refine("") {
            ensure(value.isNotBlank()) { BlankString }
            NotBlankString(value)
        }
        result shouldBeLeft BlankString
    }

    "returns right if matches predicate" {
        val result = refine("Krefty") {
            ensure(NotBlank()) { BlankString }
            NotBlankString(value)
        }
        result shouldBeRight NotBlankString("Krefty")
    }

    "returns left if doesn't match predicate" {
        val result = refine("") {
            ensure(NotBlank()) { BlankString }
            NotBlankString(value)
        }
        result shouldBeLeft BlankString
    }

    "returns right" {
        val result = dev.ustits.krefty.core.refine(NotBlank(), "test").getOrEither()
        result shouldBeRight "test"
    }

    "returns left" {
        val result = dev.ustits.krefty.core.refine(NotBlank(), "").getOrEither()
        result shouldBeLeft RefinementError
    }
})

object BlankString

@JvmInline
value class NotBlankString(private val value: String)