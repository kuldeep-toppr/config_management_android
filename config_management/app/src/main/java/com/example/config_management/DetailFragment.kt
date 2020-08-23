package com.example.config_management

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DetailFragment : Fragment() {

    private val detailAdapter by lazy {DetailAdapter()}

    private var param1: Int? = null
    private var param2: String? = null

    val api = ApiInterface.getClient().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
            adapter = detailAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        hitApi()
    }

    private fun hitApi() {
        if (param2 == "Domains") {
            val call = api.detailDomain(param1!!)
            onDetailDomain(call)
        } else if (param2 == "Features") {
            val call = api.detailFeature(param1!!)
            onDetailFeature(call)
        }
    }


    private fun onDetailDomain(call: Call<DomainDetail>) {
        call.enqueue(object : Callback<DomainDetail> {
            override fun onResponse(call: Call<DomainDetail>, response: Response<DomainDetail>) {
                if (response.isSuccessful) {
//                    Log.e("worked", "onResponse: " + response.body())
                    response.body()?.domainInfo?.featureList?.let {
                        detailAdapter.setDetailData(it)
                    }

                } else {
                    Log.e("not working", "onResponse: ")
                }
            }

            override fun onFailure(call: Call<DomainDetail>, t: Throwable) {
                Log.e("fail", "no_resp" + t.message)
            }

        })
    }

    private fun onDetailFeature(call: Call<FeatureDetail>) {
        call.enqueue(object : Callback<FeatureDetail> {
            override fun onResponse(call: Call<FeatureDetail>, response: Response<FeatureDetail>) {
                if (response.isSuccessful) {
//                    Log.e("worked", "onResponse: " + response.body())
                    response.body()?.featureInfo?.domainList?.let {
                        detailAdapter.setDetailData(it)
                    }

                } else {
                    Log.e("not working", "onResponse: ")
                }
            }

            override fun onFailure(call: Call<FeatureDetail>, t: Throwable) {
                Log.e("fail", "no_resp" + t.message)
            }

        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}