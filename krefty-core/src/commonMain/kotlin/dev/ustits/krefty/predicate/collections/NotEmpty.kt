package dev.ustits.krefty.predicate.collections

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.core.not

class NotEmpty<in C : Collection<*>> : Predicate<C> by !Empty<C>()
