plugins {
    alias(libs.plugins.wordbook.android.feature.impl)
}

android {
    namespace = "com.wordbook.feature.deckdetail.impl"
}

dependencies {
    implementation(project(":feature:deck-detail:api"))
}