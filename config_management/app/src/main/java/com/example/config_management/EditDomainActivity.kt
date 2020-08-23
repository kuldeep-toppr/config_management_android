package com.example.config_management

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditDomainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_add_domain)

        var idextras = intent.getIntExtra("id", -1)

        val api = ApiInterface.getClient().create(ApiService::class.java)

    }

}