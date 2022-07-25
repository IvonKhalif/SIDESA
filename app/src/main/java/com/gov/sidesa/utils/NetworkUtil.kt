package com.gov.sidesa.utils

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.gov.sidesa.BuildConfig
import com.gov.sidesa.utils.constants.TimeoutConstant.OKHTTP_CONNECTION_TIMEOUT
import com.gov.sidesa.utils.constants.TimeoutConstant.OKHTTP_READ_TIMEOUT
import com.gov.sidesa.utils.constants.TimeoutConstant.OKHTTP_WRITE_TIMEOUT
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NetworkUtil {
    const val BASE_URL = "https://desa.digidana.id/api/v1/"
    const val REQUEST_NOT_FOUND = "not_found"
    fun buildClient(applicationContext: Context): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

//        val requestInterceptor = Interceptor { chain ->
//            val original = chain.request()
//            val request = original.newBuilder()
//                .header(APP_VERSION, BuildConfig.VERSION_NAME.split("-").getOrNull(0).orDash())
//                .header(DEVICE_ID, DeviceUtil(applicationContext).getDeviceId())
//                .method(original.method, original.body)
//                .build()
//            chain.proceed(request)
//        }
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(OKHTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
        builder.addInterceptor(httpLoggingInterceptor)
        builder.addInterceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (!response.isSuccessful) {
                if (response.code == 401) {
                    onTokenExpired(applicationContext)
                }
            }
            response
        }
        //Enable Stetho Interceptor only at Debugging Mode
//        if (BuildConfig.DEBUG) builder.addNetworkInterceptor(StethoInterceptor())
//        builder.addInterceptor(ChuckInterceptor(applicationContext))
//        builder.addNetworkInterceptor(requestInterceptor)
//        builder.connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
        return builder.build()
    }

    private fun onTokenExpired(context: Context) {
//        if (UserUtil.getToken().isNotBlank()) {
//            PreferenceUtil.truncateStorage()
//        val intent = Intent(context, LoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        context.startActivity(intent)
//        }
    }

    inline fun <reified T> buildService(baseUrl: String, okHttpClient: OkHttpClient): T {
        val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()

        return retrofit.create(T::class.java)
    }
}