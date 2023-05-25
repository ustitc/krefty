package dev.ustits.krefty.core

import kotlin.jvm.JvmInline

@JvmInline
value class NotBlankString internal constructor(private val value: String) {

    companion object : ResultRefinery<String, NotBlankString>() {
        override fun Refinement<String>.refine() = filter { it.isNotBlank() }.map { NotBlankString(it) }
    }
}

@JvmInline
value class Name internal constructor(private val value: String) {

    companion object : ResultRefinery<String, Name>() {
        override fun Refinement<String>.refine() = filter { it.none { ch -> ch in '0'..'9' } }
            .filter(NotBlankString)
            .map { Name(it) }
    }
}