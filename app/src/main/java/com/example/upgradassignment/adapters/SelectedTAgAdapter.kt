package com.example.upgradassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.upgradassignment.R
import com.example.upgradassignment.models.Item


class SelectedTagsAdapter(tagList: ArrayList<Item>, _context: Context, val clickListener: (Item, Int) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<SelectedTagsAdapter.MyViewHolder>() {
    var tagList: ArrayList<Item>? = null
    var mContext: Context? = null
    var onItemClick: ((Item) -> Unit)? = null

    init {
        this.tagList = tagList
        this.mContext = _context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_selected, parent, false)
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


        holder.remove?.setOnClickListener {
            clickListener(tagList?.get(position)!!, position)

        }


    }


    class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var tagName: TextView? = null

        var remove: ImageButton? = null

        init {
            tagName = view.findViewById(R.id.tagsName1)
            remove = view.findViewById(R.id.removeButton)

        }
    }
}