plugins {
    alias(libs.plugins.wordbook.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.metro)
}

android {
    namespace = "com.wordbook.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.di)

    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.bundles.androidx.room)

    // Log tracker
    implementation(libs.timber)
}
