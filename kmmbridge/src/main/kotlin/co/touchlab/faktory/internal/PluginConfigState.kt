package co.touchlab.faktory.internal

import co.touchlab.faktory.KmmBridgeExtension
import java.io.File

internal data class PluginConfigState(
    val kmmBridgeExtension: KmmBridgeExtension,
    val projectDir: File,
    val buildDir: File
)