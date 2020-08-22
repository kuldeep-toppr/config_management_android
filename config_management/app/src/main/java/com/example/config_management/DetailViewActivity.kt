package com.example.config_management

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var idextras = intent.getIntExtra("id", -1)
        var oldparamextra = intent.getStringExtra("oldparam")
        setContentView(R.layout.detailed_view)
        DetailFragment.newInstance(oldparamextra!!, idextras)
    }

}