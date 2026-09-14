// swift-tools-version:5.8
import PackageDescription

let packageName = "allshared"

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v14)
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
            path: "./allshared/build/XCFrameworks/debug/\(packageName).xcframework"
        )
        ,
    ]
)