import co.touchlab.kmmbridge.artifactmanager.TestArtifactManager

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kmmbridge)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            isStatic = true
        }
    }
}

kmmbridge {
    artifactManager.set(TestArtifactManager())
    spm(swiftToolVersion = "5.8") {
        iOS { v("14") }
    }
}
