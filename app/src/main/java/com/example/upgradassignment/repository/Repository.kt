package com.example.upgradassignment.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.upgradassignment.database.Dao
import com.example.upgradassignment.database.Entity
import com.example.upgradassignment.database.MyRoomDatabase

class Repository(application: Application) {
    private val dao: Dao
    private val listLiveData: LiveData<List<Entity>>

    init {
        val habitRoomDatabase = MyRoomDatabase.getDatabase(application)
        dao = habitRoomDatabase.myDao()
        listLiveData = dao.getAllQuestions()
    }

    fun getAllQuestions(): LiveData<List<Entity>> {
        return listLiveData
    }

    fun insert(entity: Entity) {
        insertAsyncTask(dao).execute(entity)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: Dao) :
        AsyncTask<Entity, Void, Void>() {

        override fun doInBackground(vararg params: Entity): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}