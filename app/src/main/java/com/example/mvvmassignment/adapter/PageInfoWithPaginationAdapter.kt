package com.example.mvvmassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmassignment.R
import com.example.mvvmassignment.Utility
import com.example.mvvmassignment.databinding.ItemPageBinding
import com.example.mvvmassignment.model.PageModel
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.item_page.view.*


class PageInfoWithPaginationAdapter(
    private val switchChangeListener: (itemPosition: Int, item: PageModel, switch: SwitchMaterial) -> Unit,
    private val itemClickListener: (itemPosition: Int, item: PageModel, switch: SwitchMaterial) -> Unit

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
        val date= Utility().dateConverter(pageData?.created_at ?: "")
        pageData?.title =ctx.getString(R.string.title)+ " " + (pageData?.title ?: "")
        pageData?.author= ctx.getString(R.string.author)+pageData?.author
        holder.bind(pageData?: PageModel(),date)
        holder.switchSelect.setOnCheckedChangeListener { _, _ ->
            switchChangeListener(position, pageData ?: PageModel(), holder.switchSelect)
        }
        holder.itemView.setOnClickListener {
            itemClickListener(position,pageData?: PageModel(),holder.switchSelect)
        }
    }


    class PageInfoWithPaginationHolder(private var itemPageBinding: ItemPageBinding) : RecyclerView.ViewHolder(itemPageBinding.root) {
        val switchSelect: SwitchMaterial = itemView.switchSelect
        fun bind(pageData: PageModel, date: String){
              itemPageBinding.pageItems=pageData
            itemPageBinding.tvCreatedDate.text=date
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<PageModel>() {
        override fun areItemsTheSame(oldItem: PageModel, newItem: PageModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: PageModel, newItem: PageModel): Boolean {
            return oldItem == newItem
        }
    }
}