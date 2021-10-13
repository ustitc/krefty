package dev.ustits.krefty.core

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.checkAll
import io.kotest.property.forAll

class LazyRefinedTest : StringSpec({

    "correct refinement returns passed value" {
        forAll<String> {
            val refined = LazyRefined(Predicate.Stub(true), it)
            refined.unrefined == it
        }
    }

    "incorrect refinement doesn't throw error on object creation" {
        checkAll<String> {
            shouldNotThrowAny {
                LazyRefined(Predicate.Stub(false), it)
            }
        }
    }

    "incorrect refinement throws error on unrefined call" {
        checkAll<String> {
            val refined = LazyRefined(Predicate.Stub(false), it)
            shouldThrow<RefinementException> { refined.unrefined }
        }
    }

})
