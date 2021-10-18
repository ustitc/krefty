package dev.ustits.krefty.predicate.maps

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Not

class NotEmpty<M : Map<*, *>> : Predicate<M> by Not(Empty())
