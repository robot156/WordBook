import com.wordbook.convention.implementation
import com.wordbook.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("wordbook.android.library")
                apply("wordbook.android.library.compose")
                apply("dev.zacsweers.metro")
            }

            dependencies {
                implementation(project(":core:data"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:ui"))
                implementation(project(":core:di"))

                // AndroidX
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.activity.compose)
                implementation(libs.bundles.androidx.lifecycle)
                // AndroidX Navigation
                implementation(libs.androidx.navigation3.runtime)
                implementation(libs.androidx.navigation3.ui)

                // AndroidX Compose material3
                implementation(libs.androidx.compose.material3)
                implementation(libs.androidx.compose.material3.windowSizeClass)

                // Kotlin
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines)

                // ETC
                implementation(libs.bundles.coil)
                implementation(libs.timber)
            }
        }
    }
}
