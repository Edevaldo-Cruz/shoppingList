package com.example.shoppinglist.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.databinding.ActivityAddShoppBinding
import com.example.shoppinglist.databinding.ItemShoppBinding
import com.example.shoppinglist.datasource.ShoppDataSource
import com.example.shoppinglist.model.Shopp
import com.example.shoppinglist.extensions.text

class AddShoppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddShoppBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddShoppBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if(intent.hasExtra(SHOPP_ID)) {
            val shoppId = intent.getIntExtra(SHOPP_ID, 0)
            ShoppDataSource.finById(shoppId)?.let {
                binding.title.text = it.title
                binding.description.text =it.description
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.btnNewShopp.setOnClickListener {

            val shopp = Shopp(
                title = binding.title.text,
                description = binding.description.text,
                id = intent.getIntExtra(SHOPP_ID, 0)
            )
            ShoppDataSource.insertShopp(shopp)
            setResult(Activity.RESULT_OK)
            finish()

        }
    }

    companion object {
        const val SHOPP_ID = "shopp_id"
    }
}



