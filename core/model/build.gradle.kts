plugins {
    alias(libs.plugins.wordbook.jvm.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)

    compileOnly(libs.compose.stable.marker)
}