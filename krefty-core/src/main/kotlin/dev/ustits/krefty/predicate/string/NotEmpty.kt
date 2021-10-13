package dev.ustits.krefty.predicate.string

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Not

class NotEmpty : Predicate<String> by Not(Empty())
