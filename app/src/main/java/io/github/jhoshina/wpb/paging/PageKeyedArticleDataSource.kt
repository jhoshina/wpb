package io.github.jhoshina.wpb.paging

import androidx.paging.PageKeyedDataSource
import io.github.jhoshina.wpb.service.WordPressService
import io.github.jhoshina.wpb.vo.Article
import org.jsoup.Jsoup
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class PageKeyedArticleDataSource(private val service: WordPressService) : PageKeyedDataSource<Int, Article>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        call(1) { articles, next ->
            callback.onResult(articles, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        call(params.key) { articles, next ->
            callback.onResult(articles, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        //
    }

    private fun call(page: Int, callback: (articles: List<Article>, next: Int?) -> Unit) {
        val response = service.listArticles(page).execute()

        var next: Int? = null

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val articles = mutableListOf<Article>()

        response.body()?.let {
            val totalPages = response.headers().get("X-WP-TotalPages")?.toInt() ?: 0
            Timber.d("totalPages=$totalPages")
            if (page < totalPages) {
                next = page + 1
            }
            it.forEach { item ->
                val article = Article(
                    item.id,
                    sdf.parse(item.date) ?: Date(),
                    item.link,
                    item.title.rendered,
                    Jsoup.parse(item.excerpt.rendered).text(),
                    item.content.rendered,
                    item.embedded?.featuredMedia?.firstOrNull()?.sourceUrl
                )
                articles.add(article)
            }
            callback(articles, next)
        }
    }
}
