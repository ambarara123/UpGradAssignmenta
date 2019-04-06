package com.example.upgradassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.upgradassignment.R
import com.example.upgradassignment.database.Entity

class FavouriteAdapter(tagList: List<Entity>?, _context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {
    var list: ArrayList<Entity>? = null
    var mContext: Context? = null

    init {
        this.list = tagList as ArrayList<Entity>?
        this.mContext = _context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (list == null) {
            return 0
        } else {
            return (list)!!.size
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tags = list?.get(position)?.mainQuestion

        holder.tagName?.text = tags

        holder.contentHolder?.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        holder.contentHolder?.setOnClickListener {

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
