// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("com.huawei.agconnect:agcp:1.9.1.301")
    }
}