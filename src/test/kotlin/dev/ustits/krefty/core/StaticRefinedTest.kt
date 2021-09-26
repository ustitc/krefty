package dev.ustits.krefty.core

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.forAll

class StaticRefinedTest : StringSpec({

    "holds passed value" {
        forAll<String> {
            val refined = StaticRefined(it)
            refined.unrefined() == it
        }
    }
})
