package com.example.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.memo.databinding.ActivityMainBinding
import com.example.memo.databinding.ActivitySelectBinding
import com.example.memo.model.room.Entity
import com.example.memo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.recycler_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class SelectActivity : AppCompatActivity() {
    private var mBinding: ActivitySelectBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySelectBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        binding.back.setOnClickListener(){
            back()
        }
        val onlyDate: LocalDate = LocalDate.now()
        val viewModel: MainViewModel by viewModels()

        binding.date.setText(intent.getStringExtra("date").toString())
        binding.memo.setText(intent.getStringExtra("memo").toString())

        binding.update.setOnClickListener(){
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.update(
                    Entity(
                        intent.getStringExtra("id_num").toString().toInt(), binding.date.text.toString(), binding.memo.text.toString()
                    )
                )
            }
            back()
        }
        binding.delete.setOnClickListener(){
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.delete(
                    Entity(
                        intent.getStringExtra("id_num").toString().toInt(), binding.date.text.toString(), binding.memo.text.toString()
                    )
                )
            }
            back()
        }
        setContentView(binding.root)

    }
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
    fun back(){
        Intent(this, MainActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }.run {this@SelectActivity.startActivity(this)}
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_select, menu)
        return true
    }
}
