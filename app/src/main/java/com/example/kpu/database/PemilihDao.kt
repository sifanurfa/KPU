package com.example.kpu.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PemilihDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // klo insert id yg sm, bakal ada konflik, tp prosesnya bakal digagalin
    fun insert(pemilih: Pemilih)
    @Update
    fun update(pemilih: Pemilih)
    @Delete
    fun delete(pemilih: Pemilih)
    @get:Query("SELECT * from pemilih_table ORDER BY id ASC")
    val allNotes: LiveData<List<Pemilih>>
}