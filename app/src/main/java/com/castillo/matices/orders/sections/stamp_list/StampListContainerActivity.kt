package com.castillo.matices.orders.sections.stamp_list

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class StampListContainerActivity: FragmentActivity(), StampAdapter.OnStampClickListener {

    private val SELECT_STAMP_ACTIVITY_RESULT_CODE = 1

    lateinit var stampListFragment: StampListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            stampListFragment =  StampListFragment.newInstance(this)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.content, stampListFragment)
                .commit()
        }
    }

    override fun onStampClick(position: Int) {
        val intent = Intent()
        val stamp = stampListFragment.adapter.stamps[position]
        intent.putExtra("stamp", stamp)
        setResult(SELECT_STAMP_ACTIVITY_RESULT_CODE, intent)
        finish()
    }
}

