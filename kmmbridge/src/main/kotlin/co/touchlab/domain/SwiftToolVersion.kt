package co.touchlab.domain

internal class SwiftToolVersion private constructor(private val value: String) {

    val name get() = value

    internal companion object {
        internal const val DEFAULT = "5.9"

        internal fun of(version: String): SwiftToolVersion? =
            version.takeIf(String::isNotBlank)?.let(::SwiftToolVersion)
    }

    override fun equals(other: Any?): Boolean = value == (other as? SwiftToolVersion)?.value

    override fun hashCode(): Int = value.hashCode()

}