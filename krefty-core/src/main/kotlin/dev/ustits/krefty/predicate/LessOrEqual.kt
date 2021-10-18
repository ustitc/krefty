package dev.ustits.krefty.predicate

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.dsl.or

class LessOrEqual<in T>(toCompare: Comparable<T>) : Predicate<T> by Less(toCompare) or Equal(toCompare)
