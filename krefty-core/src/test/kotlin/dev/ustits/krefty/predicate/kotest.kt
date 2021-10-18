package dev.ustits.krefty.predicate

import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int

fun intNotEqualTo(value: Int): Arb<Int> {
    return Arb.int().filter { it != value }
}
