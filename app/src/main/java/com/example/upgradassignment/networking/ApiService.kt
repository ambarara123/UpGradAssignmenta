package com.example.upgradassignment.networking

import com.example.upgradassignment.models.Questions
import com.example.upgradassignment.models.TagsModel
import com.example.upgradassignment.models.UserInfo

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("me")
    fun getUserId(
        @Query("site") site: String, @Query("key") apikey: String, @Query("access_token") access_token: String
    ): Observable<UserInfo>

    @GET("tags")
    fun getTags(
        @Query("order") order: String, @Query("sort") sort: String, @Query("site") site: String
    ): Observable<TagsModel>

    @GET("questions")
    fun getQuestions(
        @Query("page") page: String,
        @Query("order") order: String, @Query("sort") sort: String, @Query("tagged") tagged: String, @Query("site") site: String
    ): Observable<Questions>


    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("http://api.stackexchange.com/2.2/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}


