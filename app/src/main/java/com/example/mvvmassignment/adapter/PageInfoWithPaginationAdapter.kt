package com.example.mvvmassignment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmassignment.R
import com.example.mvvmassignment.model.PageModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.item_page.view.*
import java.text.ParseException
import java.text.SimpleDateFormat


class PageInfoWithPaginationAdapter(private val switchChangeListener: (itemPosition: Int, item: PageModel,switch:SwitchMaterial) -> Unit
): PagingDataAdapter<PageModel, PageInfoWithPaginationAdapter.PageInfoWithPaginationHolder>(
    DiffUtilCallBack()
) {
private lateinit var ctx:Context

override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageInfoWithPaginationHolder {
        ctx=parent.context
        val layoutInflater = LayoutInflater.from(ctx)
        return PageInfoWithPaginationHolder(
            layoutInflater.inflate(
                R.layout.item_page,
                parent,
                false
            )
        )
    }



    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: PageInfoWithPaginationHolder, position: Int) {
        val pageData = getItem(position)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        try {
            val date = dateFormat.parse(pageData?.created_at?:"")
            holder.tvCratedDate.text = "Created At: $date"
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        holder.tvTitle.text = "Title:" + " " + (pageData?.title ?:"" )
        holder.tvUrl.text = pageData?.url ?: ""
        holder.switchSelect.setOnCheckedChangeListener { _, _ ->
            switchChangeListener(position,pageData?: PageModel(),holder.switchSelect)
        }
    }



    class PageInfoWithPaginationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCratedDate: MaterialTextView = itemView.tvCreatedDate
        val tvTitle: MaterialTextView = itemView.tvTitle
        val tvUrl: MaterialTextView = itemView.tvUrl
        //val cvBackgroundPageData: CardView = itemView.cvBackgroundPageData
        val switchSelect: SwitchMaterial = itemView.switchSelect
   }
    class DiffUtilCallBack : DiffUtil.ItemCallback<PageModel>() {
        override fun areItemsTheSame(oldItem: PageModel, newItem: PageModel): Boolean {
            return oldItem.title== newItem.title
        }

        override fun areContentsTheSame(oldItem: PageModel, newItem: PageModel): Boolean {
            return oldItem == newItem
        }
    }
}