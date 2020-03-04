package io.github.jhoshina.wpb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.github.jhoshina.wpb.repository.ArticleRepository
import io.github.jhoshina.wpb.vo.Article

class MainViewModel : ViewModel() {
    fun getArticles(): LiveData<PagedList<Article>> {
        val repository = ArticleRepository()
        return repository.getArticle()
    }
}
