package com.example.upgradassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.upgradassignment.R
import com.example.upgradassignment.models.Item
import com.example.upgradassignment.models.QuestionItems


class QuestionAdapter(
    tagList: ArrayList<QuestionItems>,
    _context: Context,
    val clickListener: (QuestionItems, Int) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<QuestionAdapter.MyViewHolder>() {
    var questionList: ArrayList<QuestionItems>? = null
    var mContext: Context? = null
    var onItemClick: ((Item) -> Unit)? = null

    init {
        this.questionList = tagList
        this.mContext = _context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false)
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        if (questionList == null) {
            return 0
        } else {
            return (questionList)!!.size
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tags = questionList?.get(position)?.title

        holder.tagName?.text = tags




        holder.contentHolder?.setOnClickListener {
            clickListener(questionList?.get(position)!!, position)
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