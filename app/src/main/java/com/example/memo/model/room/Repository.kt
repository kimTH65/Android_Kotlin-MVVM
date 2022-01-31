package com.example.memo.model.room
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class Repository(mDatabase: AppDatabase) {

    private val dao = mDatabase.dao()
    val allUsers: LiveData<List<Entity>> = dao.loadAllUsers()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(entity: Entity) {
        dao.insert(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(entity: Entity) {
        dao.delete(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(entity: Entity) {
        dao.update(entity)
    }

}