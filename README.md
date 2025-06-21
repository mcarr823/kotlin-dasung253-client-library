# Kotlin Dasung253 Client Library

## What is it?

This is a KMM (Kotlin Multiplatform Module) library which allows a Kotlin project to interact with a Dasung 253 monitor via its USB port.

This module abstracts away the UART commands which the monitor understands and provides simpler classes and functions for manipulating it.

It is based on [reference code from Dasung](https://www.dasung.com/h-col-112.html).

It uses the [jSerialComm](https://github.com/Fazecast/jSerialComm) and [USB Serial For Android](https://github.com/mik3y/usb-serial-for-android) libraries under the hood.

## Supported platforms

| Platform | Supported | Artifact                      |
|----------|-----------|-------------------------------|
| Core     |           | dev.mcarr.d253:library         |
| JVM      | &check;   | dev.mcarr.d253:library-jvm     |
| Android  | &check;   | dev.mcarr.d253:library-android |
| Native   | &cross;   |                               |
| iOS      | &cross;   |                               |
| Web      | &cross;   |                               |

## Example

```Kotlin

// Get a reference to the monitor
val monitor = D253SerialPortList().getMonitors().first()

// Query the monitor
val params = port.getParameters()

// Print the results
println("Threshold: ${params.threshold}")
println("Light: ${params.light}")
println("Speed: ${params.speed}")
println("Frontlight: ${params.frontlight}")
println("Enhancement: ${params.enhancement}")
println("Display mode: ${params.displayMode}")
```

## API Documentation

Javadoc can be found [here](https://mcarr823.github.io/kotlin-dasung253-client-library/).

## Setup

The setup instructions below assume that you're building a gradle project, with a TOML file for dependency management and KTS files for gradle scripts.

The instructions should still work for other setups with minor changes.

1. Add jitpack to your repositories (only necessary for Android targets):

```Kotlin
// settings.gradle.kts

dependencyResolutionManagement {
    repositories {
        // For the android serial library
        maven(url = "https://jitpack.io")
    }
}
```

2. Add the library definition and version to your TOML file (if you use one):

```toml
# libs.versions.toml

[versions]
d253 = "1.0.0"

[libraries]
d253-library-core = { module = "dev.mcarr.d253:library", version.ref = "d253" }
d253-library-jvm = { module = "dev.mcarr.d253:library-jvm", version.ref = "d253" }
d253-library-android = { module = "dev.mcarr.d253:library-android", version.ref = "d253" }
```

3. Add the dependency to your app's build.gradle.kts file for any platforms you want to support:

```Kotlin
// app (not root) build.gradle.kts

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.d253.library.core)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.d253.library.jvm)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.d253.library.android)
            }
        }
    }
}
```

## TODO

- implement the monitor detection function and logic
- add support for other platforms
- list required java version

- more docs
