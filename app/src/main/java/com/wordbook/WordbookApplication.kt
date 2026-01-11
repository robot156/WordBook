package com.wordbook

import android.app.Application
import com.wordbook.di.AppGraph
import dev.zacsweers.metro.createGraphFactory

class WordbookApplication : Application() {
    val appGraph by lazy { createGraphFactory<AppGraph.Factory>().create(this) }
}
