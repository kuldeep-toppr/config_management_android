package com.example.config_management


import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.detailed_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ARG_PARAM1 = "param1"


class DetailFragment : Fragment() {
    private var param1: String? = null
    private var id: Int? = null
    private val DetailAdapter by lazy {
        DetailAdapter()
    }

    val api = ApiInterface.getClient().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        println("**********oncreatebefore*****************")
        super.onCreate(savedInstanceState)
        println("**********oncreateafter*****************")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            id = it.getInt("id")
        }
        println("***************************************************")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.config_management.R.layout.detailed_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("*****above*******")
//
        val manager = LinearLayoutManager(requireContext())
        recyclerview2.setLayoutManager(manager)
        recyclerview2.setHasFixedSize(true)
        recyclerview2.adapter = DetailAdapter

//        recyclerview.apply {
//            setHasFixedSize(true)
//
//            layoutManager = LinearLayoutManager(requireContext())
//
//            adapter = DetailAdapter
//        }
        println("*****below*******")

    }


    override fun onResume() {
        super.onResume()
        hitApi()
    }

    private fun hitApi() {
//        if (param1 == "Domains") {
            val call = api.detailDomain(id!!)
            println("***************************************************")
            onDetailDomain(call)
//        } else if (param1 == "Features") {
//            val call = api.detailFeature()
//            onDataReceivedFeature(call)
//        }
    }



    private fun onDetailDomain(call: Call<DomainDetail>) {
        call.enqueue(object : Callback<DomainDetail> {
            override fun onResponse(call: Call<DomainDetail>, response: Response<DomainDetail>) {
                Log.e("domain detail", "final" + response.body())
//                response.body()?.featuresInfo?.let {
//                    DetailAdapter.setData(it)
            }

            override fun onFailure(call: Call<DomainDetail>, t: Throwable) {
                Log.e("fail", "no_resp" + t.message)
            }

        })
    }







    companion object {

        fun newInstance(param1: String, id: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putInt("id", id )
                }
            }

    }
}


//intent class to switch to new page