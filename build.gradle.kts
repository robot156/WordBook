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

        // main:impl에 의존성 추가
        addDependencyToMainImpl(featureName)

        println("✅ Feature modules created successfully!")
        println("   - feature:$featureName:api")
        println("   - feature:$featureName:impl")
        println("   - Added dependencies to feature:main:impl")
        println("📝 Please sync your project")
    }
}

// 하이픈으로 구분된 문자열을 PascalCase로 변환
fun String.toPascalCase(): String =
    this
        .split("-")
        .joinToString("") { it.capitalize() }

// 하이픈으로 구분된 문자열을 camelCase로 변환 (projects 접근용)
fun String.toCamelCase(): String =
    this
        .split("-")
        .mapIndexed { index, part ->
            if (index == 0) part else part.capitalize()
        }.joinToString("")

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
            |plugins {
            |   alias(libs.plugins.wordbook.android.feature.api)
            |}
            |
            |android {
            |    namespace = "com.wordbook.feature.$packageName.api"
            |}
        """.trimMargin(),
    )

    file("$srcMainDir/AndroidManifest.xml").writeText(
        """
            |<?xml version="1.0" encoding="utf-8"?>
            |<manifest xmlns:android="http://schemas.android.com/apk/res/android">
            |</manifest>
        """.trimMargin(),
    )

    // NavKey 파일 생성
    file("$navigationDir/${pascalCaseName}NavKey.kt").writeText(
        """
            |package com.wordbook.feature.$packageName.api.navigation
            |
            |import androidx.navigation3.runtime.NavKey
            |import kotlinx.serialization.Serializable
            |
            |@Serializable
            |object ${pascalCaseName}NavKey : NavKey
            |
        """.trimMargin(),
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
            |plugins {
            |   alias(libs.plugins.wordbook.android.feature.impl)
            |}
            |
            |android {
            |   namespace = "com.wordbook.feature.$packageName.impl"
            |}
            |
            |dependencies {
            |    implementation(project(":feature:$featureName:api"))
            |}
        """.trimMargin(),
    )

    file("$srcMainDir/AndroidManifest.xml").writeText(
        """
        |<?xml version="1.0" encoding="utf-8"?>
        |<manifest xmlns:android="http://schemas.android.com/apk/res/android">
        |</manifest>
        """.trimMargin(),
    )

    println("✓ Created impl module")
}

fun addToSettings(featureName: String) {
    val settingsFile = file("settings.gradle.kts")
    val content = settingsFile.readText()

    val modulesToAdd =
        """
        |include(":feature:$featureName:api")
        |include(":feature:$featureName:impl")
        """.trimMargin()

    if (!content.contains(":feature:$featureName:api")) {
        settingsFile.appendText(modulesToAdd)
        println("✓ Added modules to settings.gradle.kts")
    } else {
        println("⚠ Modules already exist in settings.gradle.kts")
    }
}

fun addDependencyToMainImpl(featureName: String) {
    val mainImplGradle = file("feature/main/impl/build.gradle.kts")

    if (!mainImplGradle.exists()) {
        println("⚠ feature:main:impl module not found, skipping dependency addition")
        return
    }

    val content = mainImplGradle.readText()
    val camelCaseName = featureName.toCamelCase()

    // 이미 의존성이 있는지 확인
    if (content.contains("projects.feature.$camelCaseName.api")) {
        println("⚠ Dependencies already exist in feature:main:impl")
        return
    }

    // dependencies 블록 찾기
    val dependenciesRegex = Regex("""(dependencies\s*\{)""")
    val match = dependenciesRegex.find(content)

    if (match != null) {
        val dependenciesToAdd = """
    implementation(projects.feature.$camelCaseName.api)
    implementation(projects.feature.$camelCaseName.impl)"""

        val insertPosition = match.range.last + 1
        val newContent =
            content.take(insertPosition) +
                dependenciesToAdd +
                content.substring(insertPosition)

        mainImplGradle.writeText(newContent)
        println("✓ Added dependencies to feature:main:impl")
    } else {
        println("⚠ Could not find dependencies block in feature:main:impl")
    }
}
