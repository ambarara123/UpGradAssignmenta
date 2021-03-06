package com.example.upgradassignment.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upgradassignment.R
import com.example.upgradassignment.activities.BrowserActivity
import com.example.upgradassignment.activities.UserInterestActivty
import com.example.upgradassignment.adapters.FavouriteAdapter
import com.example.upgradassignment.adapters.QuestionAdapter
import com.example.upgradassignment.database.Entity
import com.example.upgradassignment.database.MyRoomDatabase
import com.example.upgradassignment.models.QuestionItems
import com.example.upgradassignment.networking.ApiService
import com.example.upgradassignment.utils.Utils
import com.example.upgradassignment.viewModels.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {
    var questionAdapter: QuestionAdapter? = null

    var viewModel: ViewModel? = null
    var database: MyRoomDatabase? = null

    val apiService by lazy {
        ApiService.create()
    }
    var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        database = MyRoomDatabase.getDatabase(context!!)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        Log.d("tag", UserInterestActivty.tag!!)

        if (Utils.isNetworkAvailable(context!!)) {

            disposable =
                    apiService.getQuestions("1", "desc", "activity", UserInterestActivty.tag!!, "stackoverflow")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { result ->
                                setRcycler((result.items as ArrayList<QuestionItems>?)!!)

                                mainProgressbar.visibility = View.GONE
                            },
                            { error ->
                                Log.d("list", error.localizedMessage)
                                Utils.showToast(context!!, error.localizedMessage)
                                mainNoTxt.visibility = View.VISIBLE
                                mainProgressbar.visibility = View.GONE
                            }
                        )


        } else {
            Utils.showToast(context!!, "No network connection, Offline Mode")

            viewModel?.listQuestion?.observe(this, object : Observer<List<Entity>> {
                override fun onChanged(t: List<Entity>?) {
                    mainRecycler.layoutManager = LinearLayoutManager(context)

                    mainRecycler.adapter = FavouriteAdapter(t, context!!)
                    mainProgressbar.visibility = View.GONE

                    Log.d("live", "changed")

                }
            }


            )
        }



        return view
    }

    private fun setRcycler(arrayList: ArrayList<QuestionItems>) {
        mainRecycler.layoutManager = LinearLayoutManager(context)

        mainRecycler.adapter = QuestionAdapter(arrayList, context!!) { itemObject: QuestionItems, position: Int ->
            val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
            var saveQues = view.findViewById<ImageButton>(R.id.saveQuesButton)
            val share = view.findViewById<ImageButton>(R.id.shareButton)
            val arrow = view.findViewById<ImageButton>(R.id.arrowButton)
            val dialog = BottomSheetDialog(context!!)
            dialog.setContentView(view)
            if (!dialog.isShowing)
                dialog.show()


            arrow.setOnClickListener {
                val intent = Intent(context!!, BrowserActivity::class.java)
                intent.putExtra("url", itemObject.link)

                startActivity(intent)
                dialog.hide()

            }
            share.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, itemObject.link)
                startActivity(Intent.createChooser(shareIntent, "Share by..."))
                dialog.hide()
            }
            saveQues.setOnClickListener {
                val entity = Entity(itemObject.title!!)
                viewModel?.insert(entity)
                Utils.showToast(context!!, "Question Saved offline")
                dialog.hide()

            }
        }

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }


}
