plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kmmbridge) apply false
    alias(libs.plugins.kmmbridge.spm)
}

kmmBridgeSpm {
    excludeModules.set(setOf(":module-excluded"))
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
