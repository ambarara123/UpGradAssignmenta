package com.example.upgradassignment.networking

import retrofit2.Retrofit

class RetrofitClient private constructor() {
    private var retrofit: Retrofit? = null
/*
   public fun getClient(context: Context): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Utils.BASE_URL_LOGIN)
                .build()
        }
        return retrofit!!
    }*/

}
