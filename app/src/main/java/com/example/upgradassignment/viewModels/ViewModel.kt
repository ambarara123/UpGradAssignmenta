package com.example.upgradassignment.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.upgradassignment.database.Entity
import com.example.upgradassignment.repository.Repository


class ViewModel(application: Application) : AndroidViewModel(application) {
    var listQuestion: LiveData<List<Entity>>

    private val repository: Repository


    init {
        repository = Repository(application)
        listQuestion = repository.getAllQuestions()
    }

    fun insert(entity: Entity) {
        repository.insert(entity)
    }

}


