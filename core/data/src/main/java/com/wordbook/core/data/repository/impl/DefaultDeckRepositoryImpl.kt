package com.wordbook.core.data.repository.impl

import com.wordbook.core.data.local.dao.DeckDao
import com.wordbook.core.data.model.DeckEntity
import com.wordbook.core.data.model.DeckWithWordsEntity
import com.wordbook.core.data.model.toData
import com.wordbook.core.data.model.toDomain
import com.wordbook.core.data.repository.api.DeckRepository
import com.wordbook.model.Deck
import com.wordbook.model.DeckWithWords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultDeckRepositoryImpl(
    private val deckDao: DeckDao,
) : DeckRepository {
    override fun getAllDecks(): Flow<List<Deck>> =
        deckDao
            .getAllDecks()
            .map { decks -> decks.map(DeckEntity::toDomain) }

    override suspend fun getDeckById(deckId: Long): Deck? =
        deckDao
            .getDeckById(deckId)
            ?.toDomain()

    override fun getDeckWithWords(deckId: Long): Flow<DeckWithWords?> =
        deckDao
            .getDeckWithWords(deckId)
            .map { deckWithWords -> deckWithWords?.toDomain() }

    override fun getAllDecksWithWords(): Flow<List<DeckWithWords>> =
        deckDao
            .getAllDecksWithWords()
            .map { deckWithWords -> deckWithWords.map(DeckWithWordsEntity::toDomain) }

    override suspend fun insertDeck(deck: Deck): Long = deckDao.insertDeck(deck = deck.toData())

    override suspend fun updateDeck(deck: Deck) = deckDao.updateDeck(deck.toData())

    override suspend fun deleteDeck(deck: Deck) = deckDao.deleteDeck(deck.toData())
}
