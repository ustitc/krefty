package dev.ustits.krefty.predicate.collections

import dev.ustits.krefty.core.Predicate

class Size<C : Collection<*>>(private val size: Int) : Predicate<C> {

    override fun isRefined(value: C): Boolean {
        return value.size == size
    }
}
