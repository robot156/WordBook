plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.compose.compiler.extension)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "wordbook.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "wordbook.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "wordbook.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "wordbook.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeatureApiConvention") {
            id = "wordbook.android.feature.api"
            implementationClass = "AndroidFeatureApiConventionPlugin"
        }

        register("androidFeatureImplConvention") {
            id = "wordbook.android.feature.impl"
            implementationClass = "AndroidFeatureImplConventionPlugin"
        }

        register("jvmLibrary") {
            id = "wordbook.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }
    }
}
