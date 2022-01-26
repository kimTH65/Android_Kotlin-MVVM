package com.example.memo.model.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DAO {
    // 데이터 베이스 불러오기
    @Query("SELECT * from users ORDER BY id ASC")
    fun loadAllUsers(): LiveData<List<Entity>>

    // 데이터 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Entity)

    // 데이터 전체 삭제
    @Query("DELETE FROM users")
    fun deleteAll()

    // 데이터 업데이트
    @Update
    fun update(entity: Entity);

    // 데이터 삭제
    @Delete
    fun delete(entity: Entity);
}