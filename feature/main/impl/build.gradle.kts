plugins {
    alias(libs.plugins.wordbook.android.feature.impl)
}

android {
    namespace = "com.wordbook.feature.main.impl"
}

dependencies {
    implementation(projects.feature.deckList.api)
    implementation(projects.feature.deckList.impl)
    implementation(projects.feature.deckDetail.api)
    implementation(projects.feature.deckDetail.impl)
}
