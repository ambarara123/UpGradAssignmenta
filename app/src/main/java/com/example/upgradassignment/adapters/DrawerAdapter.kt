package com.example.upgradassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.upgradassignment.R
import com.example.upgradassignment.models.Item

class DrawerAdapter(tagList: ArrayList<Item>, _context: Context, val clickListener: (Item, Int) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<DrawerAdapter.MyViewHolder>() {
    var tagList: ArrayList<Item>? = null
    var mContext: Context? = null
    var onItemClick: ((Item) -> Unit)? = null

    companion object {
        var selectedTags: ArrayList<String> = ArrayList<String>()
    }

    init {
        this.tagList = tagList
        this.mContext = _context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (tagList == null) {
            return 0
        } else {
            return (tagList)!!.size
        }
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tags = tagList?.get(position)?.name

        holder.tagName?.text = tags

        holder.contentHolder?.setOnClickListener {
            clickListener(tagList?.get(position)!!, position)
        }


    }


    class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var tagName: TextView? = null

        var contentHolder: CardView? = null

        init {
            tagName = view.findViewById(R.id.tagsName)
            contentHolder = view.findViewById(R.id.tagLayout)


        }
    }

}