package com.example.upgradassignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {

    @Query("SELECT * from question")
    fun getAllQuestions(): LiveData<List<Entity>>

    @Insert
    fun insert(word: Entity)

    @Query("DELETE FROM question")
    fun deleteAll()

}