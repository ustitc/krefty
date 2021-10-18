package dev.ustits.krefty.predicate.maps

import dev.ustits.krefty.core.Predicate

class Size<M : Map<*, *>>(private val size: Int) : Predicate<M> {

    override fun isRefined(value: M): Boolean {
        return value.size == size
    }
}
