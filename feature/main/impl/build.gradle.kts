plugins {
    alias(libs.plugins.wordbook.android.feature.impl)
}

android {
    namespace = "com.wordbook.feature.main.impl"
}

dependencies {
    implementation(projects.feature.deckList.api)
    implementation(projects.feature.deckList.impl)
}
