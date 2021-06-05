package com.castillo.matices.orders.sections.add_order

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.castillo.matices.orders.R
import com.castillo.matices.orders.common.DatePickerFragment
import com.castillo.matices.orders.databinding.ActivityAddOrderBinding
import com.castillo.matices.orders.models.*
import com.castillo.matices.orders.viewmodels.OrderViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddOrderActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAddOrderBinding
    private val viewModel = AddOrderViewModel()

    private var documentTypes: List<IdentificationType> = emptyList()
    private var order = Order()
    private var client = Client()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_order)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editingOrder = intent.getParcelableExtra<Order>("order")
        if (editingOrder != null) {
            order = editingOrder!!
            client = editingOrder.client!!

            setOrderSentDate(order.dateSent)
            setTitle(R.string.edit_order)
        } else {
            setTitle(R.string.add_order)
        }

        binding.client = client
        binding.order = order

        binding.dateSentTextinputlayout.editText?.setOnClickListener { view ->
            showDatePickerDialog()
        }

        viewModel.getDocumentTypes {identificationTypes ->
            this.runOnUiThread {
                this.documentTypes = identificationTypes
                setupSpinner()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save_order -> {
                saveOrder()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupSpinner() {
        val types = documentTypes.map { it.name }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.identificationTypeSpinner.adapter = adapter
        binding.identificationTypeSpinner.onItemSelectedListener = this

        if (client.identificationType != null) {
            var selectedIdentificationType: Int? = null
            for ((index, value) in documentTypes.withIndex()) {
                if (client.identificationType?.id == value.id) {
                    selectedIdentificationType = index
                    break
                }
            }

            if (selectedIdentificationType != null) {
                binding.identificationTypeSpinner.setSelection(selectedIdentificationType)
            }
        }
    }

    private fun saveOrder() {
        client.identificationType = documentTypes[binding.identificationTypeSpinner.selectedItemPosition]
        viewModel.saveOrder(order, client) { success ->
            this.runOnUiThread {
                if (success) {
                    finish()
                } else {
                    val toast = Toast.makeText(applicationContext, "Error no fue posible guardar la orden", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val simpleDateFormat = SimpleDateFormat("d 'de' MMMM yyyy")
            var format = ""
            try {
                val calendar = Calendar.getInstance()
                calendar[year, month] = day
                format = simpleDateFormat.format(calendar.time)
                order.dateSent = calendar.timeInMillis
            } catch (e: Exception) {
                format = ""
            }
            binding.dateSentTextinputlayout.editText?.setText(format)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun setOrderSentDate(timestamp: Long) {
        val simpleDateFormat = SimpleDateFormat("d 'de' MMMM yyyy")
        val format = simpleDateFormat.format(Date(timestamp))
        binding.dateSentTextinputlayout.editText?.setText(format)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selected = documentTypes[p2];
    }

}
