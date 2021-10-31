package dev.ustits.krefty.predicate

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.dsl.or

class GreaterOrEqual<in T>(toCompare: Comparable<T>) : Predicate<T> by Greater(toCompare) or Equal(toCompare)
