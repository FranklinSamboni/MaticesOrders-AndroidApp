package com.castillo.matices.orders.sections.add_stamp

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import com.castillo.matices.orders.R
import com.castillo.matices.orders.databinding.ActivityAddStampBinding
import com.castillo.matices.orders.models.Stamp


class AddStampActivity : AppCompatActivity() {

    private val OPEN_DOCUMENT_CODE = 2

    private lateinit var binding: ActivityAddStampBinding
    private var stamp: Stamp = Stamp()
    private var viewModel = AddStampViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_stamp)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_stamp)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editingStamp = intent.getParcelableExtra<Stamp>("stamp")
        if (editingStamp != null) {
            stamp = editingStamp

            setTitle(R.string.edit_stamp)
            binding.deleteStamp.visibility = View.VISIBLE
        } else {
            setTitle(R.string.add_stamp)
            binding.deleteStamp.visibility = View.GONE
        }

        binding.stamp = stamp
        binding.deleteStamp.setOnClickListener { view ->
            onDeleteStampAction()
        }

        binding.stampImageView.setOnClickListener { view ->
            onPickImage()
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
                saveStamp()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === OPEN_DOCUMENT_CODE && resultCode === Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                // this is the image selected by the user
                val imageUri: Uri = data.data!!
                binding.stampImageView.setImageURI(imageUri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun saveStamp() {

        if (binding.stampImageView.drawable == null) {
            val toast = Toast.makeText(applicationContext, "Selecciona una imagen por favor", Toast.LENGTH_SHORT)
            toast.show()
            return
        }

        if (stamp.name.isEmpty()) {
            val toast = Toast.makeText(applicationContext, "Ingresa el nombre del estampado", Toast.LENGTH_SHORT)
            toast.show()
            return
        }

        val bitmap = binding.stampImageView.drawable.toBitmap()

        viewModel.saveStamp(stamp, bitmap) { success, error ->
            this.runOnUiThread {
                if (success) {
                    finish()
                } else {
                    val toast = Toast.makeText(applicationContext, "Error: $error", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }

    private fun onPickImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, OPEN_DOCUMENT_CODE)
    }

    private fun onDeleteStampAction() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("Eliminar",
                    DialogInterface.OnClickListener { dialog, id ->
                        deleteStamp()
                        dialog.dismiss()
                    })
                setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            }
            builder?.setMessage("¿Estás seguro de querer borrar este estampado?")
                ?.setTitle("Eliminar")

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show();
    }

    private fun deleteStamp() {
        viewModel.deleteStamp(stamp) { success, error ->
            this.runOnUiThread {
                if (success) {
                    finish()
                } else {
                    val toast = Toast.makeText(applicationContext, "Error: $error", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }
}