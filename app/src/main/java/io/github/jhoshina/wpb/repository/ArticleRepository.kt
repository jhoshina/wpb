package io.github.jhoshina.wpb.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.github.jhoshina.wpb.paging.ArticleDataSourceFactory
import io.github.jhoshina.wpb.service.WordPressService
import io.github.jhoshina.wpb.vo.Article
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleRepository {
    private val service: WordPressService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://blog.us.playstation.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create(WordPressService::class.java)
    }

    fun getArticle(): LiveData<PagedList<Article>> {
        val factory = ArticleDataSourceFactory(service)
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(factory, config).build()
    }
}
