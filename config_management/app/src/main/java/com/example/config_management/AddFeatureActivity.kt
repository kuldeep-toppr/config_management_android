package com.example.config_management

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_add_feature.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFeatureActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_add_feature)

        val api = ApiInterface.getClient().create(ApiService::class.java)

        buttonSubmit.setOnClickListener{


            val name = inputfeaturename.text.toString().trim()

            if (name.isEmpty()){
                inputfeaturename.error = "Name required!"
                inputfeaturename.requestFocus()
                return@setOnClickListener
            }

            val postData = Name(name)

            api.addFeature(postData)
                .enqueue(object : Callback<AddFeature>{
                    override fun onResponse(
                        call: Call<AddFeature>,
                        response: Response<AddFeature>
                    ) {
                        Log.e("didnot work", "onResponse: " + response.body()!!.errorMessage)
                        if (response.body()?.errorMessage!=null){
                            Toast.makeText(applicationContext, response.body()!!.errorMessage, Toast.LENGTH_LONG).show()
                        }
                        else{
                            val act = Intent(this@AddFeatureActivity, MainActivity::class.java)
                            this@AddFeatureActivity.startActivity(act)
                        }
                    }

                    override fun onFailure(call: Call<AddFeature>, t: Throwable) {
                        Log.e("didnot work", "onResponse: " + t.message)
                    }

                })

        }

        buttonCancel.setOnClickListener {
            val act = Intent(this@AddFeatureActivity, MainActivity::class.java)
            this@AddFeatureActivity.startActivity(act)
        }

    }

}