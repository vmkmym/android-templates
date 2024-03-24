// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    kotlin("jvm") version "1.9.23"
    // https://github.com/google/ksp/releases/tag/1.9.23-1.0.19
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}