plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.metro) apply false
}
// ./gradlew createFeatureModule -PfeatureName=모듈이름
tasks.register("createFeatureModule") {
    doLast {
        val featureName = project.findProperty("featureName") as? String

        if (featureName == null || featureName.isEmpty()) {
            error("Usage: ./gradlew createFeatureModule -PfeatureName=feature_name")
        }

        println("Creating feature module: $featureName")

        // API 모듈 생성
        createApiModule(featureName)

        // Impl 모듈 생성
        createImplModule(featureName)

        // settings.gradle.kts에 모듈 추가
        addToSettings(featureName)

        println("✅ Feature modules created successfully!")
        println("   - feature:$featureName:api")
        println("   - feature:$featureName:impl")
        println("📝 Please sync your project")
    }
}

// 하이픈으로 구분된 문자열을 PascalCase로 변환
fun String.toPascalCase(): String =
    this
        .split("-")
        .joinToString("") { it.capitalize() }

fun createApiModule(featureName: String) {
    val moduleDir = file("feature/$featureName/api")
    val packageName = featureName.replace("-", "")
    val pascalCaseName = featureName.toPascalCase()

    val srcMainDir = file("$moduleDir/src/main")
    val javaDir = file("$srcMainDir/java/com/wordbook/feature/$packageName/api")
    val navigationDir = file("$javaDir/navigation")
    val resDir = file("$srcMainDir/res")

    javaDir.mkdirs()
    navigationDir.mkdirs()
    file("$resDir/values").mkdirs()

    file("$moduleDir/build.gradle.kts").writeText(
        """
plugins {
    alias(libs.plugins.wordbook.android.feature.api)
}

android {
    namespace = "com.wordbook.feature.$packageName.api"
}
        """.trimIndent(),
    )

    file("$srcMainDir/AndroidManifest.xml").writeText(
        """
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
</manifest>
        """.trimIndent(),
    )

    // NavKey 파일 생성
    file("$navigationDir/${pascalCaseName}NavKey.kt").writeText(
        """
package com.wordbook.feature.$packageName.api.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object ${pascalCaseName}NavKey : NavKey
        """.trimIndent(),
    )

    file("$javaDir/.gitkeep").writeText("")

    println("✓ Created api module with ${pascalCaseName}NavKey.kt")
}

fun createImplModule(featureName: String) {
    val moduleDir = file("feature/$featureName/impl")
    val packageName = featureName.replace("-", "")

    val srcMainDir = file("$moduleDir/src/main")
    val javaDir = file("$srcMainDir/java/com/wordbook/feature/$packageName/impl")
    val resDir = file("$srcMainDir/res")

    javaDir.mkdirs()
    file("$resDir/values").mkdirs()

    file("$moduleDir/build.gradle.kts").writeText(
        """
plugins {
    alias(libs.plugins.wordbook.android.feature.impl)
}

android {
    namespace = "com.wordbook.feature.$packageName.impl"
}

dependencies {
    implementation(project(":feature:$featureName:api"))
}
        """.trimIndent(),
    )

    file("$srcMainDir/AndroidManifest.xml").writeText(
        """
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
</manifest>
        """.trimIndent(),
    )

    println("✓ Created impl module")
}

fun addToSettings(featureName: String) {
    val settingsFile = file("settings.gradle.kts")
    val content = settingsFile.readText()

    val modulesToAdd = """
include(":feature:$featureName:api")
include(":feature:$featureName:impl")
"""

    if (!content.contains(":feature:$featureName:api")) {
        settingsFile.appendText(modulesToAdd)
        println("✓ Added modules to settings.gradle.kts")
    } else {
        println("⚠ Modules already exist in settings.gradle.kts")
    }
}
