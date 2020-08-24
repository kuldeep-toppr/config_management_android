package com.example.config_management

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_add_domain.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddDomainActivity : AppCompatActivity(){

    var featureBool : BooleanArray? = null
    var featureNames : MutableList<String> = arrayListOf()
    var featureIds : MutableList<Int> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_add_domain)


        fun acceptList(featureNames: MutableList<String>, featureIds: MutableList<Int>){

            val convertedFeatureNames: Array<String> = featureNames.toTypedArray()


            val builder = AlertDialog.Builder(this)

            builder.setTitle("Feature List")
            builder.setMultiChoiceItems(convertedFeatureNames, featureBool){ dialog, which, isChecked ->
                featureBool!![which] = isChecked
            }



            builder.setPositiveButton("Submit"){ dialog, which ->

            }

            builder.setNegativeButton("Clear Selection"){ dialog, which ->
                featureBool = BooleanArray(convertedFeatureNames.size)
                Toast.makeText(applicationContext, "All Feature Selections have been cleared", Toast.LENGTH_LONG).show()
            }

            var dialog = builder.create()

            dialog.show()

        }

        fun onFetchFeatures(call: Call<FeaturesInfo>){
            call.enqueue(object : Callback<FeaturesInfo> {
                override fun onResponse(
                    call: Call<FeaturesInfo>,
                    response: Response<FeaturesInfo>
                ) {
                    val res = response.body()?.featuresInfo
                    Log.e("success", "gg" + res)

                    for (i in 0 until res!!.size) {
                        featureNames.add(res[i].name)
                        featureIds.add(res[i].id)
                    }
                    featureBool = BooleanArray(featureIds.size)
                    buttonfeatures.setOnClickListener {
                        acceptList(featureNames, featureIds)
                    }
                }

                override fun onFailure(call: Call<FeaturesInfo>, t: Throwable) {
                    Log.e("fail", "no_resp" + t.message)
                }

            })
        }

        val api = ApiInterface.getClient().create(ApiService::class.java)
        val call = api.fetchAllFeatures()
        onFetchFeatures(call)

        buttonSubmitDomain.setOnClickListener {


            val name = inputdomainname.text.toString().trim()

            if (name.isEmpty()) {
                inputdomainname.error = "Name required!"
                inputdomainname.requestFocus()
                return@setOnClickListener
            }
            val feature_id_list : MutableList<Int> = arrayListOf()
            for (i in 0 until (featureBool!!.size)){
                if (featureBool!![i]){

                    feature_id_list?.add(featureIds[i])
                }

            }

            val postData = AddDomain(name, feature_id_list)
            val api = ApiInterface.getClient().create(ApiService::class.java)

            api.addDomain(postData)
                .enqueue(object : Callback<AddDomainResponse> {
                    override fun onResponse(
                        call: Call<AddDomainResponse>,
                        response: Response<AddDomainResponse>
                    ) {
                        Log.e("didnot work", "onResponse: " + response.body()!!.errorMessage)
                        if (response.body()?.errorMessage != null) {
                            Toast.makeText(
                                applicationContext,
                                response.body()!!.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Domain " + response.body()!!.domainInfo.name + " has been created",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<AddDomainResponse>, t: Throwable) {
                        Log.e("didnot work", "onResponse: " + t.message)
                    }

                })


        }

        buttonCancelDomain.setOnClickListener {
            finish()
        }

    }

}
