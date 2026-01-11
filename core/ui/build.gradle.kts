plugins {
    alias(libs.plugins.wordbook.android.library)
    alias(libs.plugins.wordbook.android.library.compose)
}

android {
    namespace = "com.wordbook.ui"
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
}
