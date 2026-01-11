plugins {
    alias(libs.plugins.wordbook.android.application)
    alias(libs.plugins.wordbook.android.application.compose)
    alias(libs.plugins.metro)
}

android {
    namespace = "com.wordbook"
}

dependencies {
    implementation(projects.core.di)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.feature.main.impl)

    implementation(libs.androidx.core.ktx)
}
