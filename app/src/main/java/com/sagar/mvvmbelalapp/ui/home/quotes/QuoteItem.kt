package com.sagar.mvvmbelalapp.ui.home.quotes

import com.sagar.mvvmbelalapp.R
import com.sagar.mvvmbelalapp.data.db.entities.Quote
import com.sagar.mvvmbelalapp.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_quote
    }

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.quote = quote
    }
}