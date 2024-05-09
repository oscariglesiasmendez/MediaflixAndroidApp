package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {


    fun getApiInterface(): ApiInterface {

        return Retrofit.Builder().baseUrl("https://api.stripe.com/").addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)
    }


}