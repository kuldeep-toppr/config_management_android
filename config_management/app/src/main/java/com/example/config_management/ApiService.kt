package com.example.config_management

import retrofit2.http.GET

interface ApiService {
    @GET("domain/")
    fun fetchAllDomains(): retrofit2.Call<List<DomainClass>>
}