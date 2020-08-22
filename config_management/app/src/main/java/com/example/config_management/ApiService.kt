package com.example.config_management

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("domain/")
    fun fetchAllDomains(): retrofit2.Call<DomainsInfo>

    @GET("feature/")
    fun fetchAllFeatures(): retrofit2.Call<FeaturesInfo>



    @DELETE("domain/{id}")
    fun deleteData(@Path("id") id: Int): Call<Unit>


}