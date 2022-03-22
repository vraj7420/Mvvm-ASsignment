package com.example.mvvmassignment.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmassignment.R
import com.example.mvvmassignment.adapter.PageInfoWithPaginationAdapter
import com.example.mvvmassignment.databinding.FragmentPageDataBinding
import com.example.mvvmassignment.repository.PageDataRepository
import com.example.mvvmassignment.utils.Utility
import com.example.mvvmassignment.viewmodel.MainViewModel
import com.example.mvvmassignment.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_page_data.*
import kotlinx.coroutines.launch


class PageDataFragment : Fragment() {
    private lateinit var bindingPageDataFragment: FragmentPageDataBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PageInfoWithPaginationAdapter
    private lateinit var actionBar: ActionBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingPageDataFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_page_data, container, false)
        return bindingPageDataFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        viewModelSetUp()
        setAdapter()
        setObserver()
        setSwipeRefreshListener()
    }


    private fun setSwipeRefreshListener() {

        swipe.setOnRefreshListener {
            viewModel.loading.postValue(true)
            viewModel.recyclerViewVisibility.postValue(false)
            adapter.refresh()
            viewModel.selectedCount.value = 0
            swipe.isRefreshing = false
        }

    }

    private fun setAdapter() {
        adapter = PageInfoWithPaginationAdapter({ _, item, switchChecked ->
            if (switchChecked) {
                viewModel.selectedCount.value = viewModel.selectedCount.value?.plus(1)
                item.switchSelected = true
            } else {
                item.switchSelected = false
                viewModel.selectedCount.value = viewModel.selectedCount.value?.minus(1)
            }
        }) {position, item ->
            item.setSelectItem(!item.switchSelected)
            if (item.switchSelected) {
                viewModel.selectedCount.value = viewModel.selectedCount.value?.plus(1)
            } else {
                viewModel.selectedCount.value = viewModel.selectedCount.value?.minus(1)

            }
            adapter.notifyItemChanged(position)
        }
        rvPageData.layoutManager = LinearLayoutManager(requireContext())
        rvPageData.adapter = adapter
    }

    private fun setObserver() {
        viewModel.pageDataLiveData.observe(requireActivity()) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
        viewModel.selectedCount.observe(this) {
            if (it == 0) {
                actionBar.title = getString(R.string.app_name)
            } else {
                actionBar.title = it.toString() +" "+ getString(R.string.item_selected)
            }
        }

        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    viewModel.loading.postValue(true)
                    viewModel.error.value=""
                }
                is LoadState.Error -> {
                    viewModel.loading.postValue(false)

                    if(!Utility().checkForInternet(requireContext())){
                        viewModel.error.value=getString(R.string.no_internet_error)
                    }
                    viewModel.recyclerViewVisibility.postValue(viewModel.error.value?.isEmpty() == true && viewModel.loading.value ==false)
                }
                else -> {
                    viewModel.loading.postValue(false)
                    viewModel.recyclerViewVisibility.postValue(viewModel.error.value?.isEmpty() == true && viewModel.loading.value ==false)
                }
            }
        }
    }

    private fun viewModelSetUp() {
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(PageDataRepository())
        )[MainViewModel::class.java]
        bindingPageDataFragment.viewModel=viewModel
        bindingPageDataFragment.lifecycleOwner=activity
    }


}