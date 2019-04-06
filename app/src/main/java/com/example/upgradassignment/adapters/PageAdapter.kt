package com.example.upgradassignment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.upgradassignment.fragments.HotFragment
import com.example.upgradassignment.fragments.MainFragment
import com.example.upgradassignment.fragments.WeekFragment

class PageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return MainFragment()
        else if (position == 1)
            return HotFragment()
        else
            return WeekFragment()
    }

    override fun getCount(): Int {
        return 3
    }


}