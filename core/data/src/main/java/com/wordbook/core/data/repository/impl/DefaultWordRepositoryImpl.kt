package com.wordbook.core.data.repository.impl

import com.wordbook.core.data.local.dao.WordDao
import com.wordbook.core.data.model.WordEntity
import com.wordbook.core.data.model.toData
import com.wordbook.core.data.model.toDomain
import com.wordbook.core.data.repository.api.WordRepository
import com.wordbook.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultWordRepositoryImpl(
    private val wordDao: WordDao,
) : WordRepository {
    override fun getWordsByDeckId(deckId: Long): Flow<List<Word>> =
        wordDao
            .getWordsByDeckId(deckId)
            .map { words -> words.map(WordEntity::toDomain) }

    override suspend fun getWordById(wordId: Long): Word? =
        wordDao
            .getWordById(wordId)
            ?.toDomain()

    override fun getWordCountByDeckId(deckId: Long): Flow<Int> = wordDao.getWordCountByDeckId(deckId)

    override suspend fun insertWord(word: Word): Long = wordDao.insertWord(word.toData())

    override suspend fun insertWords(words: List<Word>) = wordDao.insertWords(words.map(Word::toData))

    override suspend fun updateWord(word: Word) = wordDao.updateWord(word.toData())

    override suspend fun deleteWord(word: Word) = wordDao.deleteWord(word.toData())

    override suspend fun deleteWordsByDeckId(deckId: Long) = wordDao.deleteWordsByDeckId(deckId)
}
