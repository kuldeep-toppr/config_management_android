package com.example.config_management

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_add_domain.*
import kotlinx.android.synthetic.main.fragment_edit_domain.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDomainActivity : AppCompatActivity(){

    var featureBool : BooleanArray? = null
    var featureNames : MutableList<String> = arrayListOf()
    var featureIds : MutableList<Int> = arrayListOf()
    var defaultFeatureIdList : MutableList<Int> = arrayListOf()
    var domain_name : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_edit_domain)

        var idextras = intent.getIntExtra("id", -1)

        val api = ApiInterface.getClient().create(ApiService::class.java)


        fun acceptList(featureNames:MutableList<String>, featureIds:MutableList<Int>){

            val convertedFeatureNames: Array<String> = featureNames.toTypedArray()


            val builder = AlertDialog.Builder(this)

            builder.setTitle("Feature List")
            builder.setMultiChoiceItems(convertedFeatureNames, featureBool){ dialog, which, isChecked ->
                featureBool!![which] = isChecked
            }

            builder.setPositiveButton("Submit"){ dialog, which ->

            }

            builder.setNeutralButton("Cancel"){ dialog, which ->
                dialog.dismiss()
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

                    for (i in 0 until res!!.size) {
                        featureNames.add(res[i].name)
                        featureIds.add(res[i].id)
                    }
                    featureBool = BooleanArray(featureIds.size)

                    for (i in 0 until featureIds.size){
                        if(featureIds[i] in defaultFeatureIdList){
                            featureBool!![i] = true
                        }
                    }

                    editdomainbuttonfeatures.setOnClickListener{
                        acceptList(featureNames, featureIds)
                    }
                }

                override fun onFailure(call: Call<FeaturesInfo>, t: Throwable) {
                    Log.e("fail", "no_resp" + t.message)
                }

            })
        }

        fun onDetailDomain(call: Call<DomainDetail>) {
            call.enqueue(object : Callback<DomainDetail> {
                override fun onResponse(call: Call<DomainDetail>, response: Response<DomainDetail>) {
                    if (response.isSuccessful) {
                        editdomainname.setText(response.body()?.domainInfo?.name)
                        for (i in 0 until response.body()!!.domainInfo.featureList.size){
                            defaultFeatureIdList.add(response.body()!!.domainInfo.featureList[i].id)
                        }
                        domain_name = response.body()!!.domainInfo.name

                        val callNew = api.fetchAllFeatures()
                        onFetchFeatures(callNew)

                    } else {
                        Log.e("not working", "onResponse: ")
                    }
                }

                override fun onFailure(call: Call<DomainDetail>, t: Throwable) {
                    Log.e("fail", "no_resp" + t.message)
                }

            })
        }

        val call = api.detailDomain(idextras)
        onDetailDomain(call)

        buttonSubmiteditDomain.setOnClickListener {


            val name = editdomainname.text.toString().trim()

            if (name.isEmpty()) {
                editdomainname.error = "Name required!"
                editdomainname.requestFocus()
                return@setOnClickListener
            }
            val feature_id_list : MutableList<Int> = arrayListOf()
            for (i in 0 until (featureBool!!.size)){
                if (featureBool!![i]){

                    feature_id_list?.add(featureIds[i])
                }

            }

            val posteditData = EditDomain(idextras, name, feature_id_list)
            val api = ApiInterface.getClient().create(ApiService::class.java)

            api.editDomain(idextras, posteditData)
                .enqueue(object : Callback<EditDomainResponse>{
                    override fun onResponse(
                        call: Call<EditDomainResponse>,
                        response: Response<EditDomainResponse>
                    ) {
                        Log.e("didnot work", "onResponse: " + response.body()!!.errorMessage)
                        if (response.body()?.errorMessage!=null){
                            Toast.makeText(applicationContext, response.body()?.errorMessage, Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(applicationContext, "Domain "+editdomainname.text+" has been edited", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<EditDomainResponse>, t: Throwable) {
                        Log.e("didnot work", "onResponse: " + t.message)
                    }

                })


        }

        buttonCanceleditDomain.setOnClickListener {
            finish()
        }

    }

}