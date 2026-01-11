package com.wordbook.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.wordbook.core.data.model.DeckEntity
import com.wordbook.core.data.model.DeckWithWordsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {
    @Query("SELECT * FROM decks ORDER BY updatedAt DESC")
    fun getAllDecks(): Flow<List<DeckEntity>>

    @Query("SELECT * FROM decks WHERE id = :deckId")
    suspend fun getDeckById(deckId: Long): DeckEntity?

    @Insert
    suspend fun insertDeck(deck: DeckEntity): Long

    @Update
    suspend fun updateDeck(deck: DeckEntity)

    @Delete
    suspend fun deleteDeck(deck: DeckEntity)

    @Transaction
    @Query("SELECT * FROM decks WHERE id = :deckId")
    fun getDeckWithWords(deckId: Long): Flow<DeckWithWordsEntity?>

    @Transaction
    @Query("SELECT * FROM decks ORDER BY updatedAt DESC")
    fun getAllDecksWithWords(): Flow<List<DeckWithWordsEntity>>
}
