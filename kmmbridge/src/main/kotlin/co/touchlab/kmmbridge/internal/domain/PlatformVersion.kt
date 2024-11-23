package co.touchlab.kmmbridge.internal.domain

internal class PlatformVersion private constructor(private val value: String) {

    val name get() = value

    internal companion object {
        internal fun of(version: String): PlatformVersion? =
            version.takeIf(String::isNotBlank)?.let(::PlatformVersion)
    }

    override fun equals(other: Any?): Boolean = value == (other as? PlatformVersion)?.value

    override fun hashCode(): Int = value.hashCode()

    override fun toString() = "PlatformVersion(name='$name')"

}