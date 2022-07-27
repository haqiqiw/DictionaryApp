package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.model.Meaning

data class MeaningDto(
    val antonyms: List<String> = listOf(),
    val definitions: List<DefinitionDto> = listOf(),
    val partOfSpeech: String = "",
    val synonyms: List<String> = listOf()
) {
    fun toMeaning(): Meaning {
        return Meaning(
            antonyms = antonyms,
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech,
            synonyms = synonyms
        )
    }
}
