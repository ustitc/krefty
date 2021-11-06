package dev.ustits.krefty.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll
import io.kotest.property.forAll

class EagerRefinedTest : StringSpec({

    "correct refinement returns passed value" {
        forAll<String> {
            val refined = EagerRefined(Predicate.Stub(true), it)
            refined.unrefined == it
        }
    }

    "incorrect refinement throws error" {
        checkAll<String> {
            shouldThrow<IllegalArgumentException> {
                EagerRefined(Predicate.Stub(false), it)
            }
        }
    }

    "refined types with equal values are equal" {
        forAll<String> {
            val predicate = Predicate.Stub<String>(true)
            val first = EagerRefined(predicate, it)
            val second = EagerRefined(predicate, it)

            first == second
        }
    }

    "refined types with different values are not equal" {
        val predicate = Predicate.Stub<Int>(true)
        val first = EagerRefined(predicate, 10)
        val second = EagerRefined(predicate, 15)

        (first != second) shouldBe true
    }

})
