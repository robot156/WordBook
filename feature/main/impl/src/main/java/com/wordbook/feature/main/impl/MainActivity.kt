package com.wordbook.feature.main.impl

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.wordbook.core.di.ActivityKey
import com.wordbook.feature.decklist.api.navigation.DeckListNavKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding

@ContributesIntoMap(AppScope::class, binding = binding<Activity>())
@ActivityKey(MainActivity::class)
@Inject
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val backStack = remember { mutableStateListOf<NavKey>(DeckListNavKey) }

            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = { key ->
                    when (key) {
                        is DeckListNavKey -> {
                            NavEntry(key) {
                                Box {}
                            }
                        }

                        else -> {
                            error("Not support NavKey")
                        }
                    }
                },
            )
        }
    }
}
