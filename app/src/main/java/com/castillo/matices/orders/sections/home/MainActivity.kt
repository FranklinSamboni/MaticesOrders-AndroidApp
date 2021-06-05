package com.castillo.matices.orders.sections.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.castillo.matices.orders.R
import com.castillo.matices.orders.databinding.ActivityMainBinding
import com.castillo.matices.orders.sections.add_order.AddOrderActivity
import com.castillo.matices.orders.sections.order_detail.OrderDetailActivity

class MainActivity : AppCompatActivity(), OrderAdapter.OnOrderClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: OrderAdapter
    private var homeViewModel = HomeViewModel()

    private val ADD_ORDER_ACTIVITY_RESULT_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
f
        setTitle(R.string.orders)

        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        getOrders()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_order -> {
                navigateToAddOrder()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onOrderClick(position: Int) {
        Log.e("TAG","MESSAGE $position")
        val order = adapter.orders[position]
        val activity = Intent(this, OrderDetailActivity::class.java)
        activity.putExtra("order", order)
        startActivity(activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === ADD_ORDER_ACTIVITY_RESULT_CODE) {
            getOrders()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun navigateToAddOrder() {   
        val activity = Intent(this, AddOrderActivity::class.java)
        startActivityForResult(activity, ADD_ORDER_ACTIVITY_RESULT_CODE);
    }

    private fun setupRecyclerView() {
        adapter = OrderAdapter(this, listOf(), this)
        binding.ordersRecyclerView?.adapter = adapter
        binding.ordersRecyclerView?.layoutManager = LinearLayoutManager(this)
    }

    private fun getOrders() {
        homeViewModel.getOrders { orders, error ->
            this.runOnUiThread {
                if (!error.isEmpty()) {
                    val toast = Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    adapter.updateOrders(orders)
                }
            }
        }
    }

}