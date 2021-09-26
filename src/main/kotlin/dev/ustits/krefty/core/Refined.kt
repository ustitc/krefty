package dev.ustits.krefty.core

interface Refined<P : Predicate<T>, T> {

    fun unrefined(): T

}
