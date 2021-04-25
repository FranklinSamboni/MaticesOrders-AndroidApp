package com.castillo.matices.orders.sections.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castillo.matices.orders.R
import com.castillo.matices.orders.sections.add_order.AddOrderActivity
import com.castillo.matices.orders.sections.order_detail.OrderDetailActivity
import com.castillo.matices.orders.models.*

class MainActivity : AppCompatActivity(), OrderAdapter.OnOrderClickListener {

    private var recyclerView: RecyclerView? = null
    private var orders = getOrders()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar))

        setTitle(R.string.orders)
        recyclerView = findViewById(R.id.orders_recycler_view)
        val adapter = OrderAdapter(this, orders, this)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
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

    private fun getOrders(): List<Order> {
        val client = Client(
                "id",
                "La Yurany Fuentes, La Yurany Fuente La Yurany Fuente",
                "Rengifo",
                2131231,
                IdentificationType.CC,
                PhoneCode.COP,
                "32112312",
                "Popayan",
                "Calle false 123")
        val products = listOf<Product>()
        val order1 = Order("qwesdqwe", OrderState.RECEIVED, "1619310988","1619310988",client,products,"Shipper")
        return listOf(order1, order1, order1)
    }

    private fun navigateToAddOrder() {
        val activity = Intent(this, AddOrderActivity::class.java)
        startActivityForResult(activity, 1);
    }

    override fun onOrderClick(position: Int) {
        Log.e("TAG","MESSAGE $position")
        val activity = Intent(this, OrderDetailActivity::class.java)
        startActivity(activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
    }
}