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