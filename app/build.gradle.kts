plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.apollographql.apollo").version("2.2.0")
}


apply(plugin = "kotlin-android")

android {
    compileSdkVersion(30)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.animesh.roy.spacexmission"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }


}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    implementation("com.apollographql.apollo:apollo-runtime:2.2.0")
    implementation("com.apollographql.apollo:apollo-coroutines-support:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.0-alpha07")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.0")
    implementation("com.github.pahlevikun:actrans:0.0.2")

}

apollo {
    generateKotlinModels.set(true)
}