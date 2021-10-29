package dev.ustits.krefty.predicate.maps

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.dsl.not

class NotEmpty<M : Map<*, *>> : Predicate<M> by !Empty<M>()
