package com.wordbook.model

data class Word(
    val id: Long = 0,
    val deckId: Long,
    val word: String,
    val mean: String,
    val createdAt: Long = System.currentTimeMillis(),
)
