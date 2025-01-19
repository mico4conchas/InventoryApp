// Root-level build.gradle.kts file
buildscript {
    repositories {
        google()        // Ensures the Android Gradle Plugin can be fetched from Google's repository
        mavenCentral()  // Ensures Kotlin plugin and other dependencies can be fetched from Maven Central
    }
    dependencies {
        // Classpath for Android Gradle Plugin
        classpath("com.android.tools.build:gradle:8.3.2")

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
