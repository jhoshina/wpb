package io.github.jhoshina.wpb.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import io.github.jhoshina.wpb.R
import io.github.jhoshina.wpb.databinding.MainFragmentBinding
import timber.log.Timber

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleAdapter = ArticleAdapter(ArticleAdapterDiffCallback())
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = articleAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getArticles().observe(viewLifecycleOwner, Observer { articles ->
            Timber.d("articles.size=${articles?.size}")
            articleAdapter.submitList(articles)
        })
    }
}
