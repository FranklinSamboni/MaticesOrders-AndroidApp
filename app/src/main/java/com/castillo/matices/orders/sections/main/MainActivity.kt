package com.castillo.matices.orders.sections.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.castillo.matices.orders.R
import com.castillo.matices.orders.databinding.ActivityMainBinding
import com.castillo.matices.orders.sections.order_list.OrderListFragment
import com.castillo.matices.orders.sections.stamp_list.StampListFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(this)
        val menuItem: MenuItem = binding.navigationView.getMenu().getItem(0)
        onNavigationItemSelected(menuItem)
        menuItem.isChecked = true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_order_list -> {
                setTitle(R.string.orders)
                val fragment: Fragment = OrderListFragment.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.home_content, fragment)
                    .commit()
            }
            R.id.nav_stamp_list -> {
                setTitle(R.string.stamps)
                val fragment: Fragment = StampListFragment.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.home_content, fragment)
                    .commit()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

}