package com.example.shoppinglist.ui

import android.R
import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.databinding.ItemShoppBinding
import com.example.shoppinglist.datasource.ShoppDataSource


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding

    private lateinit var itemShoppBinding: ItemShoppBinding

    private val adapter by lazy { ShoppListAdapter()}

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) updateList()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemShoppBinding = ItemShoppBinding.inflate(layoutInflater)
        setContentView(itemShoppBinding.root)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shopp.adapter = adapter
        updateList()
        insertListeners()
    }

    private fun insertListeners() {
        binding.btnAdd.setOnClickListener {
            // Navegação.
            register.launch(Intent(this, AddShoppActivity::class.java))
        }



       adapter.listenerConcluided = {


           val text = "Item concluido."
           val duration = Toast.LENGTH_SHORT

           val toast = Toast.makeText(applicationContext, text, duration)
           toast.show()
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddShoppActivity::class.java)
            intent.putExtra(AddShoppActivity.SHOPP_ID, it.id)
            register.launch(intent)
        }

        adapter.listenerDelete = {
            ShoppDataSource.deleteShopp(it)
            updateList()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_SHOPP && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList() {
        val list = ShoppDataSource.getList()
        binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
        else View.GONE

        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_SHOPP = 1000
    }
}


