package dev.ustits.krefty.predicate.string

import dev.ustits.krefty.core.Predicate

class OnlyArabicNumerals : Predicate<String> by MatchesRegex("[0-9]+")
