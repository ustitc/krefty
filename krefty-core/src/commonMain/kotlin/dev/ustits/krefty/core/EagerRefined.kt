package dev.ustits.krefty.core

internal class EagerRefined<P : Predicate<T>, T> private constructor(private val value: T) : Refined<P, T> {

    constructor(
        predicate: P,
        value: T
    ) : this(
        require(predicate.isRefined(value)) { "Value $value doesn't match the predicate" }
            .let { value }
    )

    override val unrefined: T
        get() = value

    override fun getOrElse(block: (T) -> T): T = value

    override fun getOrThrow(): T = value

    override fun getOrNull(): T? = value

    override fun getOrError(): Result<T> = Result.success(value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as EagerRefined<*, *>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

}
