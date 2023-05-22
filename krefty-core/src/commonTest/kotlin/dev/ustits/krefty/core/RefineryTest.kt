package dev.ustits.krefty.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.jvm.JvmInline

@JvmInline
value class NotBlankString(private val value: String) {

    companion object : Refinery<String, NotBlankString>, ResultRefinery<String, NotBlankString> {
        override fun refinement(value: String): Refinement<NotBlankString> {
            return refine(value)
                .filter { it.isNotBlank() }
                .map { NotBlankString(it) }
        }
    }
}

class RefineryTest : StringSpec({

    "returns value" {
        NotBlankString.from("test") shouldBe Result.success(NotBlankString("test"))
        NotBlankString.fromOrThrow("test") shouldBe NotBlankString("test")
        NotBlankString.fromOrNull("test") shouldBe NotBlankString("test")
        NotBlankString.fromOrError("test") shouldBe Result.success(NotBlankString("test"))
        NotBlankString.fromOrElse("test", NotBlankString("fail")) shouldBe NotBlankString("test")
        NotBlankString.fromOrElse("test") { NotBlankString("fail") } shouldBe NotBlankString("test")
    }

    "doesn't return value" {
        NotBlankString.from("") shouldBe Result.failure<RefinementException>(RefinementException(""))
        shouldThrow<RefinementException> {
            NotBlankString.fromOrThrow("")
        }
        NotBlankString.fromOrNull("") shouldBe null
        NotBlankString.fromOrError("") shouldBe Result.failure<RefinementException>(RefinementException(""))
        NotBlankString.fromOrElse("", NotBlankString("fail")) shouldBe NotBlankString("fail")
        NotBlankString.fromOrElse("") { NotBlankString("fail") } shouldBe NotBlankString("fail")
    }

})