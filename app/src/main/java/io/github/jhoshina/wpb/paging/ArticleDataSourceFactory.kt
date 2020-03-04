package io.github.jhoshina.wpb.paging

import androidx.paging.DataSource
import io.github.jhoshina.wpb.service.WordPressService
import io.github.jhoshina.wpb.vo.Article

class ArticleDataSourceFactory(service: WordPressService) : DataSource.Factory<Int, Article>() {
    val source = PageKeyedArticleDataSource(service)

    override fun create(): DataSource<Int, Article> = source
}
