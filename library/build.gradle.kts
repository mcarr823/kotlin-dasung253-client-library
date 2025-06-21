import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.dokka)
}

group = "dev.mcarr.d253"
version = "1.0.0"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    //iosX64()
    //iosArm64()
    //iosSimulatorArm64()
    //linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.usb.library.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.usb.library.jvm)
                implementation(libs.kotlinx.coroutines.core.jvm)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.usb.library.android)
                implementation(libs.kotlinx.coroutines.android)
            }
        }
    }
}

android {
    namespace = "dev.mcarr.d253.library"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "library", version.toString())

    pom {
        name = "Kotlin Dasung253 Client Library"
        description = "Kotlin Multiplatform Module for controlling Dasung253 monitors through their Serial Ports"
        inceptionYear = "2025"
        url = "https://github.com/mcarr823/kotlin-dasung253-client-library/"
        licenses {
            license {
                name = "GNU GENERAL PUBLIC LICENSE, Version 3"
                url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                distribution = "https://www.gnu.org/licenses/gpl-3.0.en.html"
            }
        }
        developers {
            developer {
                id = "mcarr823"
                name = "mcarr823"
                url = "https://github.com/mcarr823/"
            }
        }
        scm {
            url = "https://github.com/mcarr823/kotlin-dasung253-client-library/"
            connection = "scm:git:git://github.com/mcarr823/kotlin-dasung253-client-library.git"
            developerConnection = "scm:git:ssh://git@github.com/mcarr823/kotlin-dasung253-client-library.git"
        }
    }
}
