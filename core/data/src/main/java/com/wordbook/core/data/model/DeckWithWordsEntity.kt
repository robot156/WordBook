package com.wordbook.core.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.wordbook.model.DeckWithWords

// 덱과 단어들을 함께 조회하기 위한 관계 클래스
data class DeckWithWordsEntity(
    @Embedded val deck: DeckEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "deckId",
    )
    val words: List<WordEntity>,
)

internal fun DeckWithWordsEntity.toDomain(): DeckWithWords =
    DeckWithWords(
        deck = deck.toDomain(),
        words = words.map { it.toDomain() },
    )

internal fun DeckWithWords.toData(): DeckWithWordsEntity =
    DeckWithWordsEntity(
        deck = deck.toData(),
        words = words.map { it.toData() },
    )
