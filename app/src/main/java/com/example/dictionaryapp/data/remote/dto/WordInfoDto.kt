package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.domain.model.WordInfo

data class WordInfoDto(
    val license: LicenseDto = LicenseDto(),
    val meanings: List<MeaningDto> = listOf(),
    val phonetic: String = "",
    val phonetics: List<PhoneticDto> = listOf(),
    val sourceUrls: List<String> = listOf(),
    val word: String = ""
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            license = license.toLicense(),
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            phonetics = phonetics.map { it.toPhonetic() },
            sourceUrls = sourceUrls,
            word = word
        )
    }
}
