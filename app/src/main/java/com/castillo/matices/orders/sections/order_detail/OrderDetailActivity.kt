package com.castillo.matices.orders.sections.order_detail

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castillo.matices.orders.R
import com.castillo.matices.orders.sections.add_order.AddOrderActivity
import com.castillo.matices.orders.models.Color
import com.castillo.matices.orders.models.Shirt
import com.castillo.matices.orders.models.Size
import com.castillo.matices.orders.sections.products.AddProductActivity

class OrderDetailActivity : AppCompatActivity(),
    OrderDetailAdapter.OnProductClickListener {

    private lateinit var editIconImageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingAddButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        recyclerView = findViewById(R.id.products_recycler_view)
        editIconImageView = findViewById(R.id.edit_icon_imageview)
        floatingAddButton = findViewById(R.id.fab)

        setupRecyclerView()

        floatingAddButton.setOnClickListener { _ ->
            navigateToAddProduct()
        }

        editIconImageView.setOnClickListener{
            navigateToAddOrder()
        }
    }

    fun setupRecyclerView() {
        val adapter =
            OrderDetailAdapter(
                this,
                getProducts(),
                this
            )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    private fun getProducts(): List<Shirt> {
        var shirt = Shirt(Size.M,Color.Black,"asdsad","asdadasdad",true,"asdasdd",31231.23)
        return listOf(shirt,shirt,shirt)
    }

    override fun onProductClick(position: Int) {
        navigateToAddProduct()
    }

    private fun navigateToAddProduct() {
        val activity = Intent(this, AddProductActivity::class.java)
        startActivity(activity)
    }

    private fun navigateToAddOrder() {
        val activity = Intent(this, AddOrderActivity::class.java)
        startActivity(activity)
    }
}