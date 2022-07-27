package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.model.Phonetic

data class PhoneticDto(
    val audio: String = "",
    val license: LicenseDto = LicenseDto(),
    val sourceUrl: String = "",
    val text: String = ""
) {
    fun toPhonetic(): Phonetic {
        return Phonetic(
            audio = audio,
            license = license.toLicense(),
            sourceUrl = sourceUrl,
            text = text
        )
    }
}
