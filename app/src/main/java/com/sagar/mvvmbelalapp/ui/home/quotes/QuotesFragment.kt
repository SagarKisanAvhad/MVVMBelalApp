package com.sagar.mvvmbelalapp.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sagar.mvvmbelalapp.R
import com.sagar.mvvmbelalapp.databinding.QuotesFragmentBinding
import com.sagar.mvvmbelalapp.util.Coroutines
import com.sagar.mvvmbelalapp.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {


    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: QuotesViewModel by viewModels { factory }
        val binding: QuotesFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.quotes_fragment, container, false)
        binding.viewmodel = viewModel

        Coroutines.main {
            viewModel.quotes.await().observe(viewLifecycleOwner, Observer {
                context?.toast(it.size.toString())
            })
        }

        return binding.root
    }


}