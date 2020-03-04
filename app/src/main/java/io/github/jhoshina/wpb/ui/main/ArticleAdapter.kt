package io.github.jhoshina.wpb.ui.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.jhoshina.wpb.R
import io.github.jhoshina.wpb.databinding.CardArticleBinding
import io.github.jhoshina.wpb.vo.Article

class ArticleAdapter(callback: DiffUtil.ItemCallback<Article>)
    : PagedListAdapter<Article, ArticleAdapter.ArticleViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<CardArticleBinding>(inflater, R.layout.card_article, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.binding.article = article
        val link = article?.link
        if (!link.isNullOrEmpty()) {
            holder.binding.root.setOnClickListener {
                val uri = Uri.parse(link)
                val intent = CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setToolbarColor(ContextCompat.getColor(it.context, R.color.colorPrimary))
                    .build()
                intent.launchUrl(it.context, uri)
            }
        }
    }

    class ArticleViewHolder(val binding: CardArticleBinding)
        : RecyclerView.ViewHolder(binding.root)
}

class ArticleAdapterDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean
            = oldItem == newItem

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean
            = oldItem.id == newItem.id
}
