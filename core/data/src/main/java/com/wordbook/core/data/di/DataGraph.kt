package com.wordbook.core.data.di

import android.content.Context
import com.wordbook.core.data.local.db.WordbookDatabase
import com.wordbook.core.data.repository.api.DeckRepository
import com.wordbook.core.data.repository.api.WordRepository
import com.wordbook.core.data.repository.impl.DefaultDeckRepositoryImpl
import com.wordbook.core.data.repository.impl.DefaultWordRepositoryImpl
import com.wordbook.core.di.ApplicationContext
import com.wordbook.core.di.DataScope
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(DataScope::class)
interface DataGraph {
    @Binds
    val DefaultDeckRepositoryImpl.bind: DeckRepository

    @Binds
    val DefaultWordRepositoryImpl.bind: WordRepository

    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): WordbookDatabase = WordbookDatabase.buildDatabase(context)
}
