package com.example.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.memo.databinding.FragmentAddBinding
import com.example.memo.model.room.Entity
import com.example.memo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class Fragment_Add : Fragment() {
    lateinit var home_activity: MainActivity
    private var mBinding: FragmentAddBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddBinding.inflate(inflater, container, false)
        home_activity = context as MainActivity

        val onlyDate: LocalDate = LocalDate.now()
        val viewModel: MainViewModel by viewModels()
        binding.add.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.insert(
                    Entity(
                        0, onlyDate.toString(), add_memo.text.toString()
                    )
                )
            }
            val transaction = home_activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.framelayout,Fragment_Main())
            transaction.commit()
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}