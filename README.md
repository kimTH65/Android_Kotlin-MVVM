# MVVM Pattern in Android(Kotlin)
Skill : MVVM Pattern, room, liveData, viewModel, Recyclerview, dataBinding, Koin(DI)

#

<h3>1. Manifest</h3>

<h5>Manifest : Android 앱에 대한 중요한 메타 데이터가 포함된 XML</h5>

<div align="center"><h6>app/src/main/AndroidManifest.xml</div>

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.memo">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:name=".koin.MyApplication"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.Light.NoActionBar">
        <activity
            android:name=".MainActivity"

            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".SelectActivity"/>
    </application>

</manifest>
```

#

<h3>2. MyApplication</h3>

<h5>ApplicationClass : 어플리케이션내의 모든 컴포넌트에서 접근 가능, Module 전역함수를 사용해서 Koin 모듈을 선언</h5>

<div align="center"><h6>app/src/main/java/com/example/memo/koin/MyApplication.kt</div>

```
package com.example.memo.koin

import android.app.Application
import com.example.memo.Fragment_Add
import com.example.memo.model.room.Repository
import com.example.memo.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
            modules(viewModelModul)
        }
    }
    val appModule = module {
        single { Repository(get()) } // 싱글톤
    }

    val viewModelModul = module{
        viewModel { MainViewModel(get()) }
    }
}
```

#

<h3>3. Model</h3>

<h5>　Entity</h5>

<div align="center"><h6>app/src/main/java/com/example/memo/model/room/Entity.kt</div>

```
package com.example.memo.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class Entity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "memo") val memo: String?,
)
```

<h5>　DAO(Data Access Object)</h5>

<div align="center"><h6>app/src/main/java/com/example/memo/model/room/DAO.kt</div>

```
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
```
