package dev.ustits.krefty.predicate.string

import dev.ustits.krefty.core.Predicate
import dev.ustits.krefty.core.not

class NotBlank : Predicate<String> by !Blank()
