package com.example.config_management

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EntityFragment : Fragment() {
    private var param1: String? = null
    private val myAdapter by lazy {
        MyAdapter({ Toast.makeText(requireContext(), "viewClicked $it", Toast.LENGTH_SHORT).show()}, {}, {}, {
            hitApi()
        })
    }

    val api = ApiInterface.getClient().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
        }

    }

    override fun onResume() {
        super.onResume()
        hitApi()
    }

    private fun hitApi(){
        if (param1 == "Domains") {
            val call = api.fetchAllDomains()
            onDataReceived(call)
        } else {
            val call = api.fetchAllFeatures()
            onDataReceived(call)
        }
    }

    private fun onDataReceived(call: Call<DomainsInfo>) {
        call.enqueue(object : Callback<DomainsInfo> {
            override fun onResponse(call: Call<DomainsInfo>, response: Response<DomainsInfo>) {
                if (response.isSuccessful) {
                    Log.e("worked", "onResponse: " + response.body())
                    response.body()?.domainsInfo?.let {
                        myAdapter.setData(it)
                    }
                } else {
                    Log.e("not working", "onResponse: ")
                }
            }

            override fun onFailure(call: Call<DomainsInfo>, t: Throwable) {
                Log.e("fail", "no_resp" + t.message)
            }

        })
    }


    companion object {

        fun newInstance(param1: String) =
            EntityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}


//intent class to switch to new page