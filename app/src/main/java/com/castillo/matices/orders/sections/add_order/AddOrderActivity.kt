package com.castillo.matices.orders.sections.add_order

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.castillo.matices.orders.R
import com.castillo.matices.orders.models.*
import com.google.android.material.textfield.TextInputLayout
import java.lang.Exception

class AddOrderActivity : AppCompatActivity() {

    private lateinit var  clientNameTextInputLayout: TextInputLayout
    private lateinit var  identificationTypeSpinner: Spinner
    private lateinit var  identificationNumberTextInputLayout: TextInputLayout
    private lateinit var  phoneTextInputLayout: TextInputLayout
    private lateinit var  cityTextInputLayout: TextInputLayout
    private lateinit var  addressTextInputLayout: TextInputLayout
    private lateinit var  shipperTextInputLayout: TextInputLayout
    private lateinit var  dateSentTextInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        clientNameTextInputLayout = findViewById(R.id.client_name_textinputlayout)
        identificationTypeSpinner = findViewById(R.id.identification_type_spinner)
        identificationNumberTextInputLayout = findViewById(R.id.identification_number_textinputlayout)
        phoneTextInputLayout = findViewById(R.id.phone_number_textinputlayout)
        cityTextInputLayout = findViewById(R.id.city_textinputlayout)
        addressTextInputLayout = findViewById(R.id.address_textinputlayout)
        shipperTextInputLayout = findViewById(R.id.shipper_textinputlayout)
        dateSentTextInputLayout = findViewById(R.id.date_sent_textinputlayout)
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
                saveOrder()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun saveOrder() {

        val clientName = clientNameTextInputLayout.editText?.text ?: ""
        val components = clientName?.split(" ")?.toMutableList()
        val name = components?.first() ?: ""
        components?.removeAt(0)
        val lastName = components?.joinToString { " " }

        val identificationType = identificationTypeSpinner.selectedItemId
        var identificationNumber: Int = 0
        try {
            identificationNumber = (identificationNumberTextInputLayout.editText?.text ?: "").toString().toInt()
        } catch (e: Exception) {
            print(e.localizedMessage)
        }

        val phone = (phoneTextInputLayout.editText?.text ?: "").toString()
        val city = (cityTextInputLayout.editText?.text ?: "").toString()
        val address = (addressTextInputLayout.editText?.text ?: "").toString()
        val shipper = (shipperTextInputLayout.editText?.text ?: "").toString()
        val dateSent = (dateSentTextInputLayout.editText?.text ?: "").toString()

        val client = Client(
                "",
                name,
                lastName,
                identificationNumber,
                IdentificationType.CC,
                PhoneCode.COP,
                phone,
                city,
                address)

        val order = Order(
                "",
                OrderState.RECEIVED,
                "",
                dateSent,
                client,
                listOf(),
                shipper)

        val intent = Intent()
        intent.putExtra("String",true)
        setResult(5, intent)
        finish()
    }
}