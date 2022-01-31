package com.example.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.memo.databinding.FragmentAddBinding

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




        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}