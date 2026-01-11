package com.wordbook.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wordbook.core.data.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words WHERE deckId = :deckId ORDER BY createdAt DESC")
    fun getWordsByDeckId(deckId: Long): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordById(wordId: Long): WordEntity?

    @Insert
    suspend fun insertWord(word: WordEntity): Long

    @Insert
    suspend fun insertWords(words: List<WordEntity>)

    @Update
    suspend fun updateWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)

    @Query("DELETE FROM words WHERE deckId = :deckId")
    suspend fun deleteWordsByDeckId(deckId: Long)

    @Query("SELECT COUNT(*) FROM words WHERE deckId = :deckId")
    fun getWordCountByDeckId(deckId: Long): Flow<Int>
}
