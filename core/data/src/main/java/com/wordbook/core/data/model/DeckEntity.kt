package com.wordbook.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wordbook.model.Deck

@Entity(tableName = "decks")
data class DeckEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)

internal fun DeckEntity.toDomain(): Deck =
    Deck(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

internal fun Deck.toData(): DeckEntity =
    DeckEntity(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
