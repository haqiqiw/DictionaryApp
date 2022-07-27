package com.example.dictionaryapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.domain.model.License
import com.example.dictionaryapp.domain.model.Meaning
import com.example.dictionaryapp.domain.model.Phonetic
import com.example.dictionaryapp.domain.model.WordInfo

@Entity(tableName = "wordinfo_table")
data class WordInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "license") val license: License,
    @ColumnInfo(name = "meanings") val meanings: List<Meaning>,
    @ColumnInfo(name = "phonetic") val phonetic: String,
    @ColumnInfo(name = "phonetics") val phonetics: List<Phonetic>,
    @ColumnInfo(name = "source_urls") val sourceUrls: List<String>,
    @ColumnInfo(name = "word") val word: String
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            license = license,
            meanings = meanings,
            phonetic = phonetic,
            phonetics = phonetics,
            sourceUrls = sourceUrls,
            word = word
        )
    }
}
