package com.example.config_management

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EntityFragment : Fragment() {
    private var param1: String? = null
    private val myAdapter by lazy {
        MyAdapter({




        })
    }

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
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8000/")
            .build()

        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
        }

        val api = retrofit.create(ApiService::class.java)
        if (param1 == "Domains") {
            api.fetchAllDomains().enqueue(object : Callback<List<DomainClass>> {
                override fun onResponse(
                    call: Call<List<DomainClass>>,
                    response: Response<List<DomainClass>>
                ) {
                    myAdapter.myDataset = response.body()!!
                }

                override fun onFailure(call: Call<List<DomainClass>>, t: Throwable) {
                    d("fail", "no_resp")
                }

            })

        }
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