package com.castillo.matices.orders.sections.add_products

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.castillo.matices.orders.R
import com.castillo.matices.orders.databinding.ActivityAddProductBinding
import com.castillo.matices.orders.models.Color
import com.castillo.matices.orders.models.Order
import com.castillo.matices.orders.models.Product
import com.castillo.matices.orders.models.Size
import com.castillo.matices.orders.viewmodels.ProductViewModel
import java.util.*

class AddProductActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAddProductBinding
    private val viewModel = AddProductViewModel()

    private var sizes: List<Size> = emptyList()
    private var colors: List<Color> = emptyList()
    private lateinit var order: Order

    private var productViewModel = ProductViewModel(Product())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_product)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        order = intent.getParcelableExtra<Order>("order")
        val editingProduct = intent.getParcelableExtra<Product>("product")

        if (editingProduct != null) {
            productViewModel.product = editingProduct!!
            setTitle(R.string.edit_product)
            binding.deleteProduct.visibility = View.VISIBLE
        } else {
            setTitle(R.string.add_product)
            binding.deleteProduct.visibility = View.GONE
        }

        productViewModel.name = if (productViewModel.name.isEmpty()) "Camiseta" else productViewModel.name

        binding.productViewModel = productViewModel
        binding.deleteProduct.setOnClickListener { view ->
            onDeleteProductAction()
        }

        viewModel.getColors { colors ->
            this.runOnUiThread {
                this.colors = colors
                setupColorSpinner()
            }
        }

        viewModel.getSizes { sizes ->
            this.runOnUiThread {
                this.sizes = sizes
                setupSizeSpinner()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save_order -> {
                saveProduct()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun saveProduct() {
        productViewModel.size = sizes[binding.sizeSpinner.selectedItemPosition]
        productViewModel.color = colors[binding.colorSpinner.selectedItemPosition]

        viewModel.saveProduct(order, productViewModel.product) { success ->
            this.runOnUiThread {
                if (success) {
                    finish()
                } else {
                    val toast = Toast.makeText(applicationContext, "Error no fue posible guardar la información del producto", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    private fun setupSizeSpinner() {
        val types = sizes.map { it.name }
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sizeSpinner.adapter = adapter
        binding.sizeSpinner.onItemSelectedListener = this
    }

    private fun setupColorSpinner() {
        val types = colors.map { it.name }
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.colorSpinner.adapter = adapter
        binding.colorSpinner.onItemSelectedListener = this
    }

    private fun onDeleteProductAction() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("Eliminar",
                        DialogInterface.OnClickListener { dialog, id ->
                            deleteProduct()
                            dialog.dismiss()
                        })
                setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                        })
            }
            builder?.setMessage("¿Estás seguro de querer borrar este producto?")
                    ?.setTitle("Eliminar")

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show();
    }

    private fun deleteProduct() {
        viewModel.deleteProduct(productViewModel.product) { success ->
            this.runOnUiThread {
                if (success) {
                    finish()
                } else {
                    val toast = Toast.makeText(applicationContext, "Error eliminando el producto", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }
}
