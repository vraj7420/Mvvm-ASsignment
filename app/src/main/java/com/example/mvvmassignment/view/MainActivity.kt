package com.example.mvvmassignment.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmassignment.*
import com.example.mvvmassignment.adapter.PageInfoWithPaginationAdapter
import com.example.mvvmassignment.databinding.ActivityMainBinding
import com.example.mvvmassignment.repository.PageDataRepository
import com.example.mvvmassignment.view_model.MainViewModel
import com.example.mvvmassignment.view_model.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var selectedItemCount=0
    private lateinit var bindingMainActivity: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PageInfoWithPaginationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        viewModelSetUp()
        setAdapter()
        setObserver()
        setSwipeRefreshListener()
    }

    private fun setSwipeRefreshListener() {
        val  mActionBar= supportActionBar
        adapter.addLoadStateListener { loadStates ->
         swipe.isRefreshing = loadStates.refresh is LoadState.Loading
        }

        swipe.setOnRefreshListener {
         adapter.refresh()
            selectedItemCount=0
            mActionBar?.title = getString(R.string.app_name)

        }

    }

    private fun setAdapter() {
       val  mActionBar= supportActionBar
        adapter = PageInfoWithPaginationAdapter ({ _, item, switch ->
            if(switch.isChecked){
                switch.isChecked=true
                selectedItemCount+= 1
                item.isSelected = true
                mActionBar?.title = selectedItemCount.toString()
            } else {
                item.isSelected = false
                selectedItemCount -= 1
                if(selectedItemCount==0){
                    mActionBar?.title = getString(R.string.app_name)
                }else{
                mActionBar?.title = selectedItemCount.toString()
                }
            }
        }){ _, item, switch ->
            item.setSelectItem(!item.isSelected)
            switch.isChecked = item.isSelected
        }
        rvPageData.layoutManager = LinearLayoutManager(this)
        rvPageData.adapter = adapter
    }

    private fun setObserver() {
        viewModel.getPageData().observe(this@MainActivity,{
                lifecycleScope.launchWhenCreated {
                    adapter.submitData(it)
                }
            })

    }

    private fun viewModelSetUp() {
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(PageDataRepository())
        )[MainViewModel::class.java]
    }
}