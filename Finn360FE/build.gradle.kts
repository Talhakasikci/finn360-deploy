// Top-level build file
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    id("androidx.navigation.safeargs.kotlin") version "2.9.6" apply false
}