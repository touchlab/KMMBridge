package co.touchlab.faktory

import java.lang.Integer.max

internal fun prepVersionString(versionPrefix: String): String {
    val versionWithDot = versionPrefix.trim().let {
        if (it.endsWith("."))
            it
        else
            "$it."
    }

    if (versionWithDot.length == 1) {
        throw IllegalArgumentException("Version prefix format invalid: $versionPrefix")
    }

    return versionWithDot
}

internal fun maxVersion(versionPrefixTrimmed: String, versions: Sequence<String>): Int {
    return (versions
        .map { line ->
            val versionNumber = findVersionNumber(versionPrefixTrimmed, line)
            versionNumber
        }
        .filterNotNull()
        .maxOrNull() ?: 0).let { max(it, 0) }
}

internal fun findVersionNumber(versionPrefixTrimmed: String, line: String): Int? {
    val trimmed = line.trim()
    return if (trimmed.startsWith(versionPrefixTrimmed)) {
        try {
            trimmed.substring(versionPrefixTrimmed.length + 1, trimmed.length).toInt()
        } catch (e: Exception) {
            null
        }
    } else {
        null
    }
}