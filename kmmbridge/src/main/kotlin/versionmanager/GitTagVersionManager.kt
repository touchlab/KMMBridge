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

package co.touchlab.faktory.versionmanager

import co.touchlab.faktory.TEMP_PUBLISH_TAG_PREFIX
import org.gradle.api.Project

object GitTagVersionManager : GitTagBasedVersionManager() {
    override fun createMarkerVersion(project: Project, versionString: String): String? {
        return "$TEMP_PUBLISH_TAG_PREFIX$versionString"
    }

    override fun filterMarkerVersion(project: Project, versionString: String): (String) -> Boolean =
        {
            it.startsWith(
                TEMP_PUBLISH_TAG_PREFIX
            )
        }
}