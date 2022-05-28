package com.castillo.matices.orders.sections.add_products

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.castillo.matices.orders.R
import com.castillo.matices.orders.databinding.ActivityAddProductBinding
import com.castillo.matices.orders.models.*
import com.castillo.matices.orders.sections.stamp_list.StampListContainerActivity
import com.castillo.matices.orders.sections.stamp_list.StampListFragment
import com.castillo.matices.orders.viewmodels.ProductViewModel

class AddProductActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val SELECT_STAMP_ACTIVITY_RESULT_CODE = 1

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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        order = intent.getParcelableExtra<Order>("order")!!
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

        binding.selectStamp.setOnClickListener { view ->
            navigateToStampList()
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

        binding.indeterminateProgressBar.visibility = View.VISIBLE
        viewModel.saveProduct(order, productViewModel.product) { success ->
            this.runOnUiThread {
                binding.indeterminateProgressBar.visibility = View.GONE
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

    private fun navigateToStampList() {
        val activity = Intent(this, StampListContainerActivity::class.java)
        startActivityForResult(activity, SELECT_STAMP_ACTIVITY_RESULT_CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val stamp = data?.getParcelableExtra<Stamp>("stamp")
        if (requestCode == SELECT_STAMP_ACTIVITY_RESULT_CODE && stamp != null) {
            productViewModel.stamp = stamp
            binding.invalidateAll()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
