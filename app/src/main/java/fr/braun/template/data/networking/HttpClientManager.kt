package fr.braun.template.data.networking

import fr.braun.template.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


private object HttpClientManagerImpl : HttpClientManager {

    val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    override val githubRetrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}

interface HttpClientManager {

    val githubRetrofit: Retrofit


    companion object INSTANCE{
        val githubInstance: HttpClientManager = HttpClientManagerImpl
    }
}

inline fun <reified T> HttpClientManager.createGithubApi(): T {
    return this.githubRetrofit.create()
}
