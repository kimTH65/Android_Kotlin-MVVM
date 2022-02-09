package com.example.memo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.viewModelScope
import com.example.memo.model.room.AppDatabase
import com.example.memo.model.room.Entity
import com.example.memo.model.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val Repository: Repository  =
        Repository(AppDatabase.getDatabase(application, viewModelScope))
    val allUsers: LiveData<List<Entity>> = Repository.allUsers


    fun insert(entity: Entity) = viewModelScope.launch(Dispatchers.IO) {
        Repository.insert(entity)
    }


    fun delete(entity: Entity) = viewModelScope.launch(Dispatchers.IO) {
        Repository.delete(entity)
    }

    fun update(entity: Entity) = viewModelScope.launch(Dispatchers.IO) {
        Repository.update(entity)
    }

    fun getAll(): LiveData<List<Entity>>{
        return allUsers
    }

}