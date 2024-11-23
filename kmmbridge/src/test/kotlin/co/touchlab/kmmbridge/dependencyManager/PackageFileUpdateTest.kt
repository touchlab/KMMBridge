package co.touchlab.kmmbridge.dependencyManager

import co.touchlab.kmmbridge.dependencymanager.getModifiedPackageFileText
import kotlin.test.Test
import kotlin.test.assertEquals

class PackageFileUpdateTest {
    @Test
    fun realisticUrlUpdate() {
        val oldFile = """
            // swift-tools-version:5.3
            import PackageDescription

            // BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
            let remoteKotlinUrl = "https://www.example.com/"
            let remoteKotlinChecksum = "01234567890abcdef"
            let packageName = "TestPackage"
            // END KMMBRIDGE BLOCK

            let package = Package(
                name: packageName,
                platforms: [
                    .iOS(.v13)
                ],
                products: [
                    .library(
                        name: packageName,
                        targets: [packageName]
                    ),
                ],
                targets: [
                    .binaryTarget(
                        name: packageName,
                        url: remoteKotlinUrl,
                        checksum: remoteKotlinChecksum
                    )
                    ,
                ]
            )
        """.trimIndent()

        val expectedNewFile = """
            // swift-tools-version:5.3
            import PackageDescription

            // BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
            let remoteKotlinUrl = "https://www.example.com/"
            let remoteKotlinChecksum = "fedcba9876543210"
            let packageName = "TestPackage"
            // END KMMBRIDGE BLOCK

            let package = Package(
                name: packageName,
                platforms: [
                    .iOS(.v13)
                ],
                products: [
                    .library(
                        name: packageName,
                        targets: [packageName]
                    ),
                ],
                targets: [
                    .binaryTarget(
                        name: packageName,
                        url: remoteKotlinUrl,
                        checksum: remoteKotlinChecksum
                    )
                    ,
                ]
            )
        """.trimIndent()

        val newFile = getModifiedPackageFileText(
            oldFile,
            "TestPackage",
            false,
            "https://www.example.com/",
            "fedcba9876543210"
        )
        assertEquals(expectedNewFile, newFile)
    }

    @Test
    fun indentedVariables() {
        val oldFile = """
            // For some reason my variables are indented in a strange way
                // BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
                  let remoteKotlinUrl = "https://www.example.com/"
             let remoteKotlinChecksum = "01234567890abcdef"
                 let packageName = "TestPackage"
               // END KMMBRIDGE BLOCK
            // Fin
        """.trimIndent()

        val expectedNewFile = """
            // For some reason my variables are indented in a strange way
                // BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
                let remoteKotlinUrl = "https://www.example.com/"
                let remoteKotlinChecksum = "fedcba9876543210"
                let packageName = "TestPackage"
                // END KMMBRIDGE BLOCK
            // Fin
        """.trimIndent()

        val newFile = getModifiedPackageFileText(
            oldFile,
            "TestPackage",
            false,
            "https://www.example.com/",
            "fedcba9876543210"
        )
        assertEquals(expectedNewFile, newFile)
    }

    @Test
    fun withMultipleModules() {
        val oldFile = """
            // swift-tools-version:5.3
            import PackageDescription

            // BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'TestPackage' (do not edit)
            let remoteTestPackageUrl = "https://www.example.com/"
            let remoteTestPackageChecksum = "01234567890abcdef"
            let testPackagePackageName = "TestPackage"
            // END KMMBRIDGE BLOCK FOR 'TestPackage'

            // BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'TestPackage2' (do not edit)
            let remoteTestPackageUrl = "https://www.example.com/"
            let remoteTestPackageChecksum = "01234567890abcdeg"
            let testPackagePackageName = "TestPackage2"
            // END KMMBRIDGE BLOCK FOR 'TestPackage2'

            let package = Package(
                name: packageName,
                platforms: [
                    .iOS(.v13)
                ],
                products: [
                    .library(
                        name: testPackagePackageName,
                        targets: [testPackagePackageName]
                    ),
                    .library(
                        name: testPackage2PackageName,
                        targets: [testPackage2PackageName]
                    ),
                ],
                targets: [
                    .binaryTarget(
                        name: testPackagePackageName,
                        url: remoteTestPackageUrl,
                        checksum: remoteTestPackageChecksum
                    ),
                    .binaryTarget(
                        name: testPackage2PackageName,
                        url: remoteTest2PackageUrl,
                        checksum: remoteTestPackage2Checksum
                    )
                ]
            )
        """.trimIndent()

        val expectedNewFile = """
            // swift-tools-version:5.3
            import PackageDescription

            // BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'TestPackage' (do not edit)
            let remoteTestPackageUrl = "https://www.example.com/"
            let remoteTestPackageChecksum = "fedcba9876543210"
            let testPackagePackageName = "TestPackage"
            // END KMMBRIDGE BLOCK FOR 'TestPackage'

            // BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'TestPackage2' (do not edit)
            let remoteTestPackageUrl = "https://www.example.com/"
            let remoteTestPackageChecksum = "01234567890abcdeg"
            let testPackagePackageName = "TestPackage2"
            // END KMMBRIDGE BLOCK FOR 'TestPackage2'

            let package = Package(
                name: packageName,
                platforms: [
                    .iOS(.v13)
                ],
                products: [
                    .library(
                        name: testPackagePackageName,
                        targets: [testPackagePackageName]
                    ),
                    .library(
                        name: testPackage2PackageName,
                        targets: [testPackage2PackageName]
                    ),
                ],
                targets: [
                    .binaryTarget(
                        name: testPackagePackageName,
                        url: remoteTestPackageUrl,
                        checksum: remoteTestPackageChecksum
                    ),
                    .binaryTarget(
                        name: testPackage2PackageName,
                        url: remoteTest2PackageUrl,
                        checksum: remoteTestPackage2Checksum
                    )
                ]
            )
        """.trimIndent()

        val newFile = getModifiedPackageFileText(
            oldFile,
            "TestPackage",
            true,
            "https://www.example.com/",
            "fedcba9876543210"
        )
        assertEquals(expectedNewFile, newFile)
    }
}
