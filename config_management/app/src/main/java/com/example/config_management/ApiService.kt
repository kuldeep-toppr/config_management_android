package com.example.config_management

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("domain/")
    fun fetchAllDomains(): retrofit2.Call<DomainsInfo>

    @GET("feature/")
    fun fetchAllFeatures(): retrofit2.Call<FeaturesInfo>

    @DELETE("domain/{id}/")
    fun deleteDomain(@Path("id") id: Int): Call<DeleteDomain>

    @DELETE("feature/{id}/")
    fun deleteFeature(@Path("id") id: Int): Call<DeleteFeature>

    @GET("domain/{id}/")
    fun detailDomain(@Path("id") id: Int): Call<DomainDetail>

}