package com.example.config_management

import retrofit2.http.GET

interface ApiService {
    @GET("domain/")
    fun fetchAllDomains(): retrofit2.Call<DomainsInfo>

    @GET("feature/")
    fun fetchAllFeatures(): retrofit2.Call<FeaturesInfo>
}