package com.castillo.matices.orders.sections.order_detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.castillo.matices.orders.R
import com.castillo.matices.orders.databinding.ActivityOrderDetailBinding
import com.castillo.matices.orders.sections.add_order.AddOrderActivity
import com.castillo.matices.orders.models.Order
import com.castillo.matices.orders.models.Product
import com.castillo.matices.orders.sections.add_products.AddProductActivity
import com.castillo.matices.orders.sections.home.HomeViewModel
import com.castillo.matices.orders.viewmodels.OrderViewModel

class OrderDetailActivity : AppCompatActivity(), OrderDetailAdapter.OnProductClickListener, OrderDetailAdapter.OnCheckedChangedListener {

    private val ADD_PRODUCT_ACTIVITY_RESULT_CODE = 10

    private lateinit var binding: ActivityOrderDetailBinding
    private var viewModel = OrderDetailViewModel()
    private lateinit var orderViewModel: OrderViewModel

    private lateinit var orderDetailAdapter: OrderDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_order_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.order_detail)

        orderViewModel = OrderViewModel(intent.getParcelableExtra<Order>("order"))
        binding.content.orderViewModel = orderViewModel
        setupRecyclerView()

        binding.fab.setOnClickListener { _ ->
            navigateToAddProduct(null)
        }

        binding.content.editIconImageview.setOnClickListener{
            navigateToEditOrder()
        }

        binding.content.deleteOrder.setOnClickListener { view ->
            onDeleteOrderAction()
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getOrderUpdated(orderViewModel.order) {order ->
            this.runOnUiThread {
                if (order != null) {
                    this.orderViewModel.order = order
                    binding.content.orderViewModel = orderViewModel
                }
                orderDetailAdapter.updateProducts(order?.products ?: listOf())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun setupRecyclerView() {
        orderDetailAdapter = OrderDetailAdapter(this, listOf(), this, this)
        binding.content.productsRecyclerView.adapter = orderDetailAdapter
        binding.content.productsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onProductClick(position: Int) {
        navigateToAddProduct(orderDetailAdapter.products[position])
    }

    override fun onCheckedChanged(position: Int, isChecked: Boolean) {
        val product = orderDetailAdapter.products[position]
        if (isChecked !== product.isStampCutted) {
            product.isStampCutted = isChecked
            viewModel.updateProduct(product) { success ->
                this.runOnUiThread {
                    if (!success) {
                        val toast = Toast.makeText(applicationContext, "Error actualizando el estado de corte del producto", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
            }
        }
    }

    private fun navigateToAddProduct(product: Product?) {
        val activity = Intent(this, AddProductActivity::class.java)
        activity.putExtra("order", orderViewModel.order)
        activity.putExtra("product", product)
        startActivityForResult(activity, ADD_PRODUCT_ACTIVITY_RESULT_CODE)
    }

    private fun navigateToEditOrder() {
        val activity = Intent(this, AddOrderActivity::class.java)
        activity.putExtra("order", orderViewModel.order)
        startActivity(activity)
    }

    private fun onDeleteOrderAction() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("Eliminar",
                        DialogInterface.OnClickListener { dialog, id ->
                            deleteOrder()
                            dialog.dismiss()
                        })
                setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                        })
            }
            builder?.setMessage("¿Estás seguro de querer borrar esta Orden?")
                    ?.setTitle("Eliminar")

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show();
    }

    private fun deleteOrder() {
        viewModel.deleteOrder(orderViewModel.order) { success ->
            this.runOnUiThread {
                if (success) {
                    finish()
                } else {
                    val toast = Toast.makeText(applicationContext, "Error eliminando la orden", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }


}