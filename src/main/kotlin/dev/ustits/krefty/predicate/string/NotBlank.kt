package dev.ustits.krefty.predicate.string

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.predicate.logical.Not

class NotBlank : Predicate<String> by Not(Blank())
