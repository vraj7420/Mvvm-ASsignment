package com.example.mvvmassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmassignment.databinding.ActivityMainBinding
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
        adapter.addLoadStateListener { loadStates ->
         swipe.isRefreshing = loadStates.refresh is LoadState.Loading
        }
        swipe.setOnRefreshListener {

        }
    }

    private fun setAdapter() {
       val  mActionBar= supportActionBar
        adapter = PageInfoWithPaginationAdapter { _, item, switch ->
            if(switch.isChecked){
                selectedItemCount+= 1
                item.isSelected = true
                mActionBar?.title = selectedItemCount.toString()
            } else {
                item.isSelected = false
                selectedItemCount -= 1
                mActionBar?.title = selectedItemCount.toString()
            }
        }
        rvPageData.layoutManager = LinearLayoutManager(this)
        rvPageData.adapter = adapter
    }

    private fun setObserver() {
        viewModel.getPageData1().observe(this@MainActivity,{
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