package com.example.upgradassignment.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upgradassignment.R
import com.example.upgradassignment.adapters.SelectedTagsAdapter
import com.example.upgradassignment.adapters.TagsAdapter
import com.example.upgradassignment.models.Item
import com.example.upgradassignment.networking.ApiService
import com.example.upgradassignment.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_interest_activty.*

class UserInterestActivty : AppCompatActivity() {
    var tagAdapter: TagsAdapter? = null
    var selectedtagList: ArrayList<Item>? = null
    var selectedTagsAdapter: SelectedTagsAdapter? = null

    companion object {
        var tag: String? = null
    }

    val apiService by lazy {
        ApiService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_interest_activty)
        this.getSharedPreferences("app", Context.MODE_PRIVATE)
            .getBoolean("login", false)

        //if not logged in
        if (
            !this.getSharedPreferences("app", Context.MODE_PRIVATE)
                .getBoolean("login", false)
        ) {
            startActivity(Intent(this@UserInterestActivty, MainActivity::class.java))
            this@UserInterestActivty.finish()
        }


        selectedtagList = ArrayList<Item>()

        selectedTagsAdapter = SelectedTagsAdapter(selectedtagList!!, this) { itemObject: Item, position: Int ->

        }

        tagRecycler.layoutManager = LinearLayoutManager(this)
        //selectedtagRecycler.layoutManager =LinearLayoutManager(this)


        disposable =
                apiService.getTags("desc", "popular", "stackoverflow")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            setRcycler((result.items as ArrayList<Item>?)!!)
                            tagProgress.visibility = View.GONE
                        },
                        { error ->
                            Log.d("list", error.localizedMessage)
                            interestNoTxt.visibility = View.VISIBLE
                            Utils.showToast(this, error.localizedMessage)
                            tagProgress.visibility = View.GONE
                        }
                    )

        submitButton.setOnClickListener {
            if (selectedtagList?.size == 4) {
                var intent = Intent(this@UserInterestActivty, QuestionListActivity::class.java)
                tag = selectedtagList?.get(0)?.name

                intent.putParcelableArrayListExtra("selected_list", selectedtagList)

                startActivity(intent)
            } else {
                Toast.makeText(this@UserInterestActivty, "tags must be equal to four", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun setRcycler(songList: ArrayList<Item>) {

        tagRecycler.adapter = TagsAdapter(songList, this@UserInterestActivty) { itemObject: Item, position: Int ->
            if (selectedtagList?.size == 4) {
            } else {
                selectedtagList?.add(itemObject)
                selectedTagsAdapter = SelectedTagsAdapter(
                    selectedtagList!!,
                    this@UserInterestActivty
                ) { itemObject: Item, position: Int ->
                    selectedtagList?.removeAt(position)
                    selectedTagsAdapter?.notifyDataSetChanged()

                }
                selectedtagRecycler.adapter = selectedTagsAdapter
                Log.d("click", position.toString() + itemObject.count)
            }

        }

    }

    override fun onPause() {
        super.onPause()
        disposable!!.dispose()
    }
}



