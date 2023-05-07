package dev.ustits.krefty.core

class RefinementException(val value: Any?) : IllegalArgumentException("Value ($value) doesn't match the predicate") {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RefinementException

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
