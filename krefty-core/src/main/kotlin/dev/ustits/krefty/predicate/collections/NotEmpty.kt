package dev.ustits.krefty.predicate.collections

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Not

class NotEmpty<C : Collection<T>, T> : Predicate<C> by Not(Empty())
