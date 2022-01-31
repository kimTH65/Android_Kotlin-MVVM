package com.example.memo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.memo.databinding.ActivityMainBinding
import com.example.memo.databinding.ActivitySelectBinding
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.recycler_item.*

class SelectActivity : AppCompatActivity() {
    private var mBinding: ActivitySelectBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySelectBinding.inflate(layoutInflater)

        binding.idNum.setText(intent.getStringExtra("in_num").toString())
        binding.date.setText(intent.getStringExtra("date").toString())
        binding.memo.setText(intent.getStringExtra("memo").toString())
        setContentView(binding.root)

    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}
