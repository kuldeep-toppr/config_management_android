package com.example.config_management

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var idextras = intent.getIntExtra("id", -1)
        var oldparamextra = intent.getStringExtra("oldparam")

        println(idextras)

        val pagerAdapter = DetailPagerAdapter(supportFragmentManager, idextras, oldparamextra!!)

        setContentView(R.layout.fragment_detail)
        viewpagerdetail.adapter = pagerAdapter

        textView5.text = idextras.toString()

        if (oldparamextra=="Domains"){
            textView6.text = "Associated Features -> "
        }
        else{
            textView6.text = "Domains Associated to -> "

        }

        buttonEdit.setOnClickListener {
            if (oldparamextra=="Domains"){
                val intent = Intent(this, EditDomainActivity::class.java)
                intent.putExtra("id", idextras)
                this.startActivity(intent)
            }
            else{
                val intent = Intent(this, EditFeatureActivity::class.java)
                intent.putExtra("id", idextras)
                this.startActivity(intent)
            }
        }

        buttonDelete.setOnClickListener {
            val delete_id = idextras
            val api = ApiInterface.getClient().create(ApiService::class.java)

            fun onDeleteDomain(call: Call<DeleteDomain>) {
                call.enqueue(object : Callback<DeleteDomain> {
                    override fun onFailure(call: Call<DeleteDomain>, t: Throwable) {
                        Log.e("fail", "no_resp" + t.message)
                    }

                    override fun onResponse(call: Call<DeleteDomain>, response: Response<DeleteDomain>) {
                        println(response.body())
                        }
                    })
                }

            fun onDeleteFeature(call: Call<DeleteFeature>) {
                call.enqueue(object : Callback<DeleteFeature> {
                    override fun onFailure(call: Call<DeleteFeature>, t: Throwable) {
                        Log.e("fail", "no_resp" + t.message)
                    }

                    override fun onResponse(call: Call<DeleteFeature>, response: Response<DeleteFeature>) {
                        println(response.body())
                    }

                })
            }

            val myQuittingDialogBox: AlertDialog =
                AlertDialog.Builder(this) // set message, title, and icon
                    .setTitle("Delete")
                    .setMessage("Do you want to Delete")
                    .setPositiveButton("Delete",
                        DialogInterface.OnClickListener { dialog, _ -> //your deleting code

                            if (oldparamextra == "Domains") {
                                val call = api.deleteDomain(delete_id)
                                onDeleteDomain(call)
                            } else if (oldparamextra == "Features") {
                                val call = api.deleteFeature(delete_id)
                                onDeleteFeature(call)
                            }
                            finish()
                        })
                    .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
                    .create()
            myQuittingDialogBox.show()

        }

    }

}

class DetailPagerAdapter(fm: FragmentManager, idinclass:Int, name:String) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return 1
    }

    private val id = idinclass
    private val name = name

    override fun getItem(position: Int): Fragment {
        return DetailFragment.newInstance(id,name)
    }

}