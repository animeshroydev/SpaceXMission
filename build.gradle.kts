buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

