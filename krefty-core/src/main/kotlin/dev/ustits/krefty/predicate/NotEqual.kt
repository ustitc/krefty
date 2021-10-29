package dev.ustits.krefty.predicate

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.dsl.not

class NotEqual<in T>(private val toCompare: T) : Predicate<T> by !Equal(toCompare)
