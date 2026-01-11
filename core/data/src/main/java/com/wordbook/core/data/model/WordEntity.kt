package com.wordbook.core.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.wordbook.model.Word

@Entity(
    tableName = "words",
    foreignKeys = [
        ForeignKey(
            entity = DeckEntity::class,
            parentColumns = ["id"],
            childColumns = ["deckId"],
            onDelete = ForeignKey.CASCADE, // 덱 삭제 시 단어도 함께 삭제
        ),
    ],
    indices = [Index(value = ["deckId"])], // 검색 성능 향상
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val deckId: Long,
    val word: String,
    val mean: String,
    val createdAt: Long = System.currentTimeMillis(),
)

internal fun WordEntity.toDomain(): Word =
    Word(
        id = id,
        deckId = deckId,
        word = word,
        mean = mean,
        createdAt = createdAt,
    )

internal fun Word.toData(): WordEntity =
    WordEntity(
        id = id,
        deckId = deckId,
        word = word,
        mean = mean,
        createdAt = createdAt,
    )
