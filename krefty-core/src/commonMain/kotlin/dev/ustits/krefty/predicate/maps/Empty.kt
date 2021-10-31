package dev.ustits.krefty.predicate.maps

import dev.ustits.krefty.core.Predicate

class Empty<M : Map<*, *>> : Predicate<M> {

    override fun isRefined(value: M): Boolean {
        return value.isEmpty()
    }
}
