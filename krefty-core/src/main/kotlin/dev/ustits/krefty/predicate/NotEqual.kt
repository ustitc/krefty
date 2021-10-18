package dev.ustits.krefty.predicate

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Not

class NotEqual<T>(private val toCompare: T) : Predicate<T> by Not(Equal(toCompare))
