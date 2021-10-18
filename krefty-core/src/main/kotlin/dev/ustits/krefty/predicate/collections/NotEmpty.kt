package dev.ustits.krefty.predicate.collections

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Not

class NotEmpty<C : Collection<*>> : Predicate<C> by Not(Empty())
