package dev.ustits.krefty.arrow

import dev.ustits.krefty.core.refine
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec

class RefinementTest : StringSpec({

    "returns right" {
        val result = refine("test", String::isNotBlank).getOrEither()
        result shouldBeRight "test"
    }

    "returns left" {
        val result = refine("", String::isNotBlank).getOrEither()
        result shouldBeLeft RefinementError
    }
})
