pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()

        // For the android serial library used by dev.mcarr.usb
        // https://github.com/mik3y/usb-serial-for-android
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "kotlin-dasung253-client-library"
include(":library")
