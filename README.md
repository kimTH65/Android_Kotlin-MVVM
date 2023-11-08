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

<div align="center"><h5>Entity</h5><h6>app/src/main/java/com/example/memo/model/room/Entity.kt</div>

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

<div align="center"><h5>DAO(Data Access Object)</h5><br><h6>app/src/main/java/com/example/memo/model/room/DAO.kt</div>

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

<div align="center"><h5>Room Database</h5><br><h6>app/src/main/java/com/example/memo/model/room/AppDatabase.kt</div>

```
package com.example.memo.model.room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


@Database(entities = arrayOf(Entity::class),  version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                    ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
```

<div align="center"><h5>Repository</h5><br><h6>app/src/main/java/com/example/memo/model/room/AppDatabase.kt</div>

```
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
```

#

<h3>4. ViewModel</h3>

<div align="center"><h6>app/src/main/java/com/example/memo/viewModel/MainViewModel.kt</div>

```
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
```

#

<h3>5. View</h3>

<h5>Activity, Fragment</h5>

<div align="center"><h6>app/src/main/java/com/example/memo/~</div>

```
...
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.framelayout,Fragment_Main())
        transaction.commit()
...
```

<h5>이하처럼 ViewModel사용</h5>

```
...
class Fragment_Main : Fragment() {
    lateinit var home_activity: MainActivity
    private var mBinding: FragmentMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        home_activity = context as MainActivity

        val viewModel: MainViewModel by viewModel()
        val mAdapter = RecyclerViewAdapter(home_activity , viewModel)

        val LinearManager = LinearLayoutManager(home_activity)
        LinearManager.reverseLayout = true
        LinearManager.stackFromEnd = true

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearManager

        }
        viewModel.allUsers.observe(this, Observer { users ->
            // Update the cached copy of the users in the adapter.
            users?.let { mAdapter.setUsers(it) }
        })
...
```

<h5>XML</h5>

<div align="center"><h6>app/src/main/res/~</div>

```
...
        <androidx.appcompat.widget.Toolbar xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#FFAC07"
            android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light">
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="메모장"
                android:layout_gravity="center"
                android:id="@+id/toolbar_title"
                android:textSize="20sp"
                />
        </androidx.appcompat.widget.Toolbar>

        <FrameLayout
            android:id="@+id/framelayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        </FrameLayout>
...
```
