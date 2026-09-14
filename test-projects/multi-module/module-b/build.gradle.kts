import co.touchlab.kmmbridge.test.TestArtifactManager

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kmmbridge)
}

group = providers.gradleProperty("GROUP").get()
version = providers.gradleProperty("LIBRARY_VERSION").get()

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "ModuleB"
            isStatic = true
        }
    }
}

kmmbridge {
    testArtifacts()
    spm(swiftToolVersion = "5.8", spmDirectory = "../") {
        iOS { v("14") }
    }
}
