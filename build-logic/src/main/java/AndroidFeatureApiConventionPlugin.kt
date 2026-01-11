import com.wordbook.convention.api
import com.wordbook.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("wordbook.android.library")
                apply("wordbook.android.library.compose")
            }

            dependencies {
                api(libs.androidx.navigation3.runtime)
                api(libs.androidx.navigation3.ui)
            }
        }
    }
}
