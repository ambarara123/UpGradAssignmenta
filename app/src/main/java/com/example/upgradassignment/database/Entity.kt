package com.example.upgradassignment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
class Entity(
    @ColumnInfo(name = "question")
    val mainQuestion: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}



