package com.example.upgradassignment.activities


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upgradassignment.R
import com.example.upgradassignment.activities.UserInterestActivty.Companion.tag
import com.example.upgradassignment.adapters.DrawerAdapter
import com.example.upgradassignment.adapters.PageAdapter
import com.example.upgradassignment.models.Item
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_question_list.*
import kotlinx.android.synthetic.main.app_bar_question_list.*
import kotlinx.android.synthetic.main.content_question_list.*


class QuestionListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var selectedtagList: ArrayList<Item>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.upgradassignment.R.layout.activity_question_list)
        setSupportActionBar(toolbar)


        val pageAdapter = PageAdapter(supportFragmentManager)

        viewPager.adapter = pageAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))


        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            com.example.upgradassignment.R.string.navigation_drawer_open,
            com.example.upgradassignment.R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)



        if (intent != null && intent.hasExtra("selected_list")) {

            drawerRecycler.layoutManager = LinearLayoutManager(this)

            selectedtagList = intent.getParcelableArrayListExtra("selected_list")

            drawerRecycler.adapter = DrawerAdapter(selectedtagList!!, this) { itemObject: Item, position: Int ->
                //  yourTab.isSelected = true

                tag = selectedtagList?.get(position)!!.name!!

                drawer_layout.closeDrawer(GravityCompat.START)

                if (viewPager.currentItem == 0)
                    viewPager.currentItem = 2
                else if (viewPager.currentItem == 2)
                    viewPager.currentItem = 0
                else
                    viewPager.currentItem = 0

                viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))


            }


        }
        tabListener()


    }


    private fun tabListener() {
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))


            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.question_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
