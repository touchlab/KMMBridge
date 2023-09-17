/*
 * Copyright (c) 2023 Touchlab.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package co.touchlab.faktory.internal

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
            trimmed.substring(versionPrefixTrimmed.length, trimmed.length).toInt()
        } catch (e: Exception) {
            null
        }
    } else {
        null
    }
}