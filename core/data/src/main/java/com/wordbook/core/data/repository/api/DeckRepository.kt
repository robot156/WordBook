package com.wordbook.core.data.repository.api

import com.wordbook.model.Deck
import com.wordbook.model.DeckWithWords
import kotlinx.coroutines.flow.Flow

interface DeckRepository {
    fun getAllDecks(): Flow<List<Deck>>

    suspend fun getDeckById(deckId: Long): Deck?

    fun getDeckWithWords(deckId: Long): Flow<DeckWithWords?>

    fun getAllDecksWithWords(): Flow<List<DeckWithWords>>

    suspend fun insertDeck(deck: Deck): Long

    suspend fun updateDeck(deck: Deck)

    suspend fun deleteDeck(deck: Deck)
}
