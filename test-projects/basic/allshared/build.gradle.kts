import co.touchlab.kmmbridge.test.TestArtifactManager

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kmmbridge)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            isStatic = true
        }
    }
}

kmmbridge {
    testUploadArtifacts()
    spm(swiftToolVersion = "5.8") {
        iOS { v("14") }
    }
}
