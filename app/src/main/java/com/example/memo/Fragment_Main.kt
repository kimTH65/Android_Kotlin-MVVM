package com.example.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memo.databinding.FragmentMainBinding
import com.example.memo.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}