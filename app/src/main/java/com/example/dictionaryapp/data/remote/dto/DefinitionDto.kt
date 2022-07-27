package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.model.Definition

data class DefinitionDto(
    val antonyms: List<String> = listOf(),
    val definition: String = "",
    val example: String = "",
    val synonyms: List<String> = listOf()
) {
    fun toDefinition(): Definition {
        return Definition(
            antonyms = antonyms,
            definition = definition,
            example = example,
            synonyms = synonyms
        )
    }
}
