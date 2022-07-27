package com.example.dictionaryapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionaryapp.data.local.converter.WordInfoConverter
import com.example.dictionaryapp.data.local.dao.WordInfoDao
import com.example.dictionaryapp.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.data.util.JsonParserImpl

@Database(
    entities = [WordInfoEntity::class],
    version = 1
)
@TypeConverters(WordInfoConverter::class)
abstract class WordInfoDatabase : RoomDatabase() {

    abstract fun wordInfoDao(): WordInfoDao

    companion object {
        @Volatile
        private var instance: WordInfoDatabase? = null

        fun create(context: Context): WordInfoDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = buildDatabase(context)
                }
            }
            return instance as WordInfoDatabase
        }

        private fun buildDatabase(context: Context): WordInfoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                WordInfoDatabase::class.java,
                "wordinfo_database"
            ).addTypeConverter(WordInfoConverter())
                .build()
        }
    }
}
