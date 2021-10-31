package dev.ustits.krefty.predicate.collections

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.string.NotBlank
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class EachTest: StringSpec({

    "returns true if each element is refined by a predicate" {
        val elementPredicate = Predicate.Stub<String>(true)
        val predicate = Each<List<String>, String>(elementPredicate)

        val result = predicate.isRefined(listOf("test 1", "test 2"))

        result shouldBe true
    }

    "returns true if collections is empty" {
        val elementPredicate = Predicate.Stub<String>(true)
        val predicate = Each<List<String>, String>(elementPredicate)

        val result = predicate.isRefined(emptyList())

        result shouldBe true
    }

    "returns false if any element is not refined by a predicate" {
        val elementPredicate = NotBlank()
        val predicate = Each<List<String>, String>(elementPredicate)

        val result = predicate.isRefined(listOf("test 1", "     ", "test 2"))

        result shouldBe false
    }

})
