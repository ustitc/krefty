package dev.ustits.krefty.predicate.collections

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SizeTest : StringSpec({

    "returns true if list size is of specified size" {
        val size = 13
        val list = List(size) { it }
        val predicate = Size<List<Int>, Int>(size)

        predicate.isRefined(list) shouldBe true
    }

    "returns false if list is smaller" {
        val size = 13
        val list = List(size / 2) { it }
        val predicate = Size<List<Int>, Int>(size)

        predicate.isRefined(list) shouldBe false
    }

    "returns false if list is larger" {
        val size = 13
        val list = List(size * 2) { it }
        val predicate = Size<List<Int>, Int>(size)

        predicate.isRefined(list) shouldBe false
    }

})
