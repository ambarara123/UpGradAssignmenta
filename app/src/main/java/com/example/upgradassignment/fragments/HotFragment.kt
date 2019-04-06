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

class HotFragment : Fragment() {
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

        val view = inflater.inflate(R.layout.fragment_hot, container, false)


        database = MyRoomDatabase.getDatabase(context!!)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)

        if (Utils.isNetworkAvailable(context!!)) {
            Log.d("network", "reached")

            disposable =
                    apiService.getQuestions("1", "desc", "hot", UserInterestActivty.tag!!, "stackoverflow")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { result ->
                                setRcycler((result.items as ArrayList<QuestionItems>?)!!)
                                hotProgressbar.visibility = View.GONE
                            },
                            { error ->
                                Log.d("list", error.localizedMessage)
                                Utils.showToast(context!!, error.message!!)
                                hotNoTxt.visibility = View.VISIBLE
                                hotProgressbar.visibility = View.GONE
                            }
                        )
        } else {
            Utils.showToast(context!!, "No network connection, Offline Mode")

            viewModel?.listQuestion?.observe(this, object : Observer<List<Entity>> {
                override fun onChanged(t: List<Entity>?) {
                    hotRecycler.layoutManager = LinearLayoutManager(context)

                    hotRecycler.adapter = FavouriteAdapter(t, context!!)
                    hotProgressbar.visibility = View.GONE

                    Log.d("live", "changed")

                }
            }

            )
        }

        return view
    }

    private fun setRcycler(arrayList: ArrayList<QuestionItems>) {
        hotRecycler.layoutManager = LinearLayoutManager(context)

        hotRecycler.adapter = QuestionAdapter(arrayList, context!!) { itemObject: QuestionItems, position: Int ->
            val view1 = layoutInflater.inflate(R.layout.bottom_sheet, null)
            val share = view1.findViewById<ImageButton>(R.id.shareButton)
            val arrow = view1.findViewById<ImageButton>(R.id.arrowButton)
            var saveQues = view1.findViewById<ImageButton>(R.id.saveQuesButton)
            val dialog = BottomSheetDialog(context!!)
            dialog.setContentView(view1)
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
