package com.wordbook.core.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wordbook.core.data.local.dao.DeckDao
import com.wordbook.core.data.local.dao.WordDao
import com.wordbook.core.data.model.DeckEntity
import com.wordbook.core.data.model.WordEntity

@Database(
    entities = [DeckEntity::class, WordEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class WordbookDatabase : RoomDatabase() {
    abstract fun deckDao(): DeckDao

    abstract fun wordDao(): WordDao

    companion object {
        private const val DATABASE_NAME = "wordbook-db"

        fun buildDatabase(context: Context): WordbookDatabase =
            Room
                .databaseBuilder(context, WordbookDatabase::class.java, DATABASE_NAME)
                .enableMultiInstanceInvalidation()
                .build()
    }
}
