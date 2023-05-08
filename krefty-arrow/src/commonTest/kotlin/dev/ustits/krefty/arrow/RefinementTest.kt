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
            ensure(it.isNotBlank()) {
                BlankString
            }
            NotBlankString(it)
        }
        result shouldBeRight NotBlankString("Krefty")
    }

    "returns left if doesn't match condition" {
        val result = refine("") {
            ensure(it.isNotBlank()) {
                BlankString
            }
            NotBlankString(it)
        }
        result shouldBeLeft BlankString
    }

    "returns right if matches predicate" {
        val result = refine("Krefty") {
            refine(NotBlank(), it) {
                BlankString
            }
            NotBlankString(it)
        }
        result shouldBeRight NotBlankString("Krefty")
    }

    "returns left if doesn't match predicate" {
        val result = refine("") {
            refine(NotBlank(), it) {
                BlankString
            }
            NotBlankString(it)
        }
        result shouldBeLeft BlankString
    }
})

object BlankString

@JvmInline
value class NotBlankString(private val value: String)