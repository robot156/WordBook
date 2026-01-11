package com.wordbook.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        defaultConfig {
            minSdk = WordbookConfig.MIN_SDK
            compileSdk = WordbookConfig.COMPILE_SDK
        }

        compileOptions {
            sourceCompatibility = WordbookConfig.javaCompileTarget
            targetCompatibility = WordbookConfig.javaCompileTarget
            isCoreLibraryDesugaringEnabled = true
        }

        buildFeatures {
            buildConfig = true
        }
    }

    configureKotlin()
    dependencies {
        coreLibraryDesugaring(libs.android.desugarJdkLibs)
    }
}

internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = WordbookConfig.javaCompileTarget
        targetCompatibility = WordbookConfig.javaCompileTarget
    }

    configureKotlin()
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(WordbookConfig.javaCompileTarget.toString()))

            freeCompilerArgs.addAll(
                "-Xstring-concat=inline",
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
                // Enable experimental compose APIs
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
                "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",

                // Enable Annotation API
                "-XXLanguage:+PropertyParamAnnotationDefaultTargetMode",
            )
        }
    }
}