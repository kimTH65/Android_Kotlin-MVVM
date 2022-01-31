package com.example.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memo.databinding.ActivityMainBinding
import com.example.memo.model.room.Entity
import com.example.memo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel

        binding.toolbar.setSupportActionBar(menu)
        binding.framelayout
        val transaction = supportFragmentManager.beginTransaction()
        var fragmentName = Fragment_Main()
        transaction.add(R.id.framelayout,fragmentName)
        transaction.commit()


        /*val mAdapter = RecyclerViewAdapter(this, viewModel)
        recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        viewModel.allUsers.observe(this, Observer { users ->
            // Update the cached copy of the users in the adapter.
            users?.let { mAdapter.setUsers(it) }
        })

        val onlyDate: LocalDate = LocalDate.now()

        button.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.insert(
                    Entity(
                        0, onlyDate.toString(),edit.text.toString())
                )
            }

        }*/

    }




}