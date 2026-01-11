pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "WordBook"
include(":app")
include(":core:data")
include(":core:designsystem")
include(":core:ui")
include(":core:model")
include(":core:di")
include(":feature:main:impl")
include(":feature:deck-list:api")
include(":feature:deck-list:impl")

include(":feature:deck-detail:api")
include(":feature:deck-detail:impl")
