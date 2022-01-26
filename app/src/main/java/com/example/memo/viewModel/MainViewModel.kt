package com.example.memo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memo.model.room.AppDatabase
import com.example.memo.model.room.Entity
import com.example.memo.model.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val Repository: Repository =
        Repository(AppDatabase.getDatabase(application, viewModelScope))

    val allUsers: LiveData<List<Entity>> = Repository.allUsers


    fun insert(entity: Entity) = viewModelScope.launch(Dispatchers.IO) {
        Repository.insert(entity)
    }


    fun deleteAll(entity: Entity) = viewModelScope.launch(Dispatchers.IO) {
        Repository.delete(entity)
    }

    fun getAll(): LiveData<List<Entity>>{
        return allUsers
    }

}