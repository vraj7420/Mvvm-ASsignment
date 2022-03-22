package com.example.mvvmassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmassignment.R
import com.example.mvvmassignment.databinding.ItemPageBinding
import com.example.mvvmassignment.model.PageModel
import com.example.mvvmassignment.utils.Utility
import kotlinx.android.synthetic.main.item_page.view.*


class PageInfoWithPaginationAdapter(
    private val switchOnClickListener: (itemPosition: Int, item: PageModel, switchChecked: Boolean) -> Unit,
    private val itemClickListener: (itemPosition: Int, item: PageModel) -> Unit

) : PagingDataAdapter<PageModel, PageInfoWithPaginationAdapter.PageInfoWithPaginationHolder>(
    DiffUtilCallBack()
) {
    private lateinit var ctx: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageInfoWithPaginationHolder {
        ctx = parent.context
        val binding: ItemPageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_page, parent,
            false
        )
        return PageInfoWithPaginationHolder(binding)
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: PageInfoWithPaginationHolder, position: Int) {
        val pageData = getItem(position)
        if (pageData?.switchSelected != true) {
            holder.switchSelect.isChecked = false
        }
        val date = Utility().dateConverter(pageData?.created_at ?: "")
        val title = (position + 1).toString() + "." + (pageData?.title ?: "")
        holder.switchSelect.setOnClickListener {
            switchOnClickListener(position, pageData ?: PageModel(), holder.switchSelect.isChecked)
        }
        holder.itemView.setOnClickListener {
            itemClickListener(position, pageData ?: PageModel(),)
        }
        val selected= pageData?.switchSelected
        holder.bind(PageModel(date, title, pageData?.author ?: "", selected == true))

    }


    class PageInfoWithPaginationHolder(private var itemPageBinding: ItemPageBinding) :
        RecyclerView.ViewHolder(itemPageBinding.root) {
        val switchSelect: SwitchCompat = itemView.switchSelect
        fun bind(pageData: PageModel) {
            itemPageBinding.pageItems = pageData
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<PageModel>() {
        override fun areItemsTheSame(oldItem: PageModel, newItem: PageModel): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: PageModel, newItem: PageModel): Boolean {
            return false
        }
    }
}