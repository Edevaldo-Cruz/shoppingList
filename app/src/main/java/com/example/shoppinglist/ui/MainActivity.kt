package com.example.shoppinglist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.datasource.ShoppDataSource


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    private val adapter by lazy { ShoppListAdapter()}

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) updateList()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvShopp.adapter = adapter
        updateList()
        insertListeners()
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            // Navegação.
            register.launch(Intent(this, AddShoppActivity::class.java))
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
        private const val   CREATE_NEW_SHOPP = 1000
    }
}
