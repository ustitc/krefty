package dev.ustits.krefty.core

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec

class FailedRefinedTest : StringSpec({

    "throws passed exception" {
        val refined = FailedRefined<Predicate<String>, String>(IllegalStateException())
        shouldThrowExactly<IllegalStateException> { refined.unrefined() }
    }

})
