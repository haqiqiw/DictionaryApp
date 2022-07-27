package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.model.License

data class LicenseDto(
    val name: String = "",
    val url: String = ""
) {
    fun toLicense(): License {
        return License(
            name = name,
            url = url
        )
    }
}
