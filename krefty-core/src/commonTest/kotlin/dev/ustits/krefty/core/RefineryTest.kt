package dev.ustits.krefty.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.jvm.JvmInline

@JvmInline
value class NotBlankString internal constructor(private val value: String) {

    companion object : ResultRefinery<String, NotBlankString>() {
        override fun Refinement<String>.refine() = filter { it.isNotBlank() }.map { NotBlankString(it) }
    }
}

@JvmInline
value class Name internal constructor(private val value: String) {

    companion object : ResultRefinery<String, Name>() {
        override fun Refinement<String>.refine() = filter { it.none { ch -> ch in '0'..'9' } }
            .filter(NotBlankString)
            .map { Name(it) }
    }
}

class RefineryTest : StringSpec({

    "returns value" {
        Name.from("Scanlan") shouldBe Result.success(Name("Scanlan"))
        Name.fromOrThrow("Scanlan") shouldBe Name("Scanlan")
        Name.fromOrNull("Scanlan") shouldBe Name("Scanlan")
        Name.fromOrError("Scanlan") shouldBe Result.success(Name("Scanlan"))
        Name.fromOrElse("Scanlan", Name("fail")) shouldBe Name("Scanlan")
        Name.fromOrElse("Scanlan") { Name("fail") } shouldBe Name("Scanlan")
    }

    "doesn't return value" {
        Name.from("Scan1an") shouldBe Result.failure<RefinementException>(RefinementException("Scan1an"))
        shouldThrow<RefinementException> {
            Name.fromOrThrow("Sc@Scan1an")
        }
        Name.fromOrNull("Scan1an") shouldBe null
        Name.fromOrError("Scan1an") shouldBe Result.failure<RefinementException>(RefinementException("Scan1an"))
        Name.fromOrElse("Scan1an", Name("fail")) shouldBe Name("fail")
        Name.fromOrElse("Scan1an") { Name("fail") } shouldBe Name("fail")
    }

})