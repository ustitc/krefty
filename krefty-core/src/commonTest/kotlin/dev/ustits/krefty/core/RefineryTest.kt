package dev.ustits.krefty.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

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