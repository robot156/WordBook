import com.android.build.api.dsl.ApplicationExtension
import com.wordbook.convention.Plugins
import com.wordbook.convention.WordbookConfig
import com.wordbook.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.ANDROID_APPLICATION)
                apply(Plugins.KOTLIN_ANDROID)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig.apply {
                    applicationId = WordbookConfig.APPLICATION_ID
                    targetSdk = WordbookConfig.TARGET_SDK
                    versionCode = WordbookConfig.VERSION_CODE
                    versionName = WordbookConfig.VERSION_NAME
                }
            }
        }
    }
}