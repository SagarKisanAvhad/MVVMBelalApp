package com.sagar.mvvmbelalapp.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sagar.mvvmbelalapp.R
import com.sagar.mvvmbelalapp.data.db.entities.Quote
import com.sagar.mvvmbelalapp.databinding.QuotesFragmentBinding
import com.sagar.mvvmbelalapp.util.Coroutines
import com.sagar.mvvmbelalapp.util.hide
import com.sagar.mvvmbelalapp.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()
    private val viewModel: QuotesViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: QuotesFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.quotes_fragment, container, false)
        bindUi()
        return binding.root
    }

    private fun bindUi() {
        Coroutines.main {
            progress_bar.show()
            viewModel.quotes.await().observe(viewLifecycleOwner, Observer { quotes ->
                progress_bar.hide()
                initRecyclerView(quotes.mapToQuoteItems())
            })
        }
    }


    private fun initRecyclerView(quoteItems: List<QuoteItem>) {
        val section = Section()
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            add(section)
        }

        rv_quote.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        section.addAll(quoteItems)

    }

    private fun List<Quote>.mapToQuoteItems() = map { QuoteItem(it) }


}