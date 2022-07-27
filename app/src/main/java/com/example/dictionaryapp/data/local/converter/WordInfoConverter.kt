package com.example.dictionaryapp.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dictionaryapp.data.util.JsonParser
import com.example.dictionaryapp.data.util.JsonParserImpl
import com.example.dictionaryapp.domain.model.License
import com.example.dictionaryapp.domain.model.Meaning
import com.example.dictionaryapp.domain.model.Phonetic
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class WordInfoConverter(
    private val jsonParser: JsonParser = JsonParserImpl()
) {

    @TypeConverter
    fun fromLicenseJson(json: String): License {
        return jsonParser.fromJson<License>(
            json,
            object : TypeToken<License>() {}.type
        ) ?: License("", "")
    }

    @TypeConverter
    fun toLicenseJson(license: License): String {
        return jsonParser.toJson(
            license,
            object : TypeToken<License>() {}.type
        ) ?: "{}"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<List<Meaning>>(
            json,
            object : TypeToken<List<Meaning>>() {}.type
        ).orEmpty()
    }

    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<List<Meaning>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromPhoneticsJson(json: String): List<Phonetic> {
        return jsonParser.fromJson<List<Phonetic>>(
            json,
            object : TypeToken<List<Phonetic>>() {}.type
        ).orEmpty()
    }

    @TypeConverter
    fun toPhoneticsJson(phonetics: List<Phonetic>): String {
        return jsonParser.toJson(
            phonetics,
            object : TypeToken<List<Phonetic>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromStringsJson(json: String): List<String> {
        return jsonParser.fromJson<List<String>>(
            json,
            object : TypeToken<List<String>>() {}.type
        ).orEmpty()
    }

    @TypeConverter
    fun toStringsJson(strings: List<String>): String {
        return jsonParser.toJson(
            strings,
            object : TypeToken<List<String>>() {}.type
        ) ?: "[]"
    }
}
