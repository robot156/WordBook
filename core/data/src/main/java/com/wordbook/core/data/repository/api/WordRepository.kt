package com.wordbook.core.data.repository.api

import com.wordbook.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun getWordsByDeckId(deckId: Long): Flow<List<Word>>

    suspend fun getWordById(wordId: Long): Word?

    fun getWordCountByDeckId(deckId: Long): Flow<Int>

    suspend fun insertWord(word: Word): Long

    suspend fun insertWords(words: List<Word>)

    suspend fun updateWord(word: Word)

    suspend fun deleteWord(word: Word)

    suspend fun deleteWordsByDeckId(deckId: Long)
}
