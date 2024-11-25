package com.example.kpu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Pemilih::class], version = 1, exportSchema = false)
abstract class PemilihRoomDatabase : RoomDatabase() {
    abstract fun pemilihDao(): PemilihDao?
    companion object {
        @Volatile
        private var INSTANCE: PemilihRoomDatabase? = null
        fun getDatabase(context: Context): PemilihRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(PemilihRoomDatabase::class.java) {
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        PemilihRoomDatabase::class.java, "pemilih_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}