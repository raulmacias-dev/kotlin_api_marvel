package com.raul.macias.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.raul.macias.wikiheroes.databinding.FavCharacterRowBinding
import com.raul.macias.wikiheroes.models.Character
import com.raul.macias.wikiheroes.view.ui.home.ItemCharacterViewModel

class FavCharacterViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private lateinit var character: Character

    private val binding by lazy { DataBindingUtil.bind<FavCharacterRowBinding>(view) }



    override fun bindData(data: Any?) {
        if (data is Character) {
            character = data
            drawUI()
        }
    }

    private fun drawUI() {

        binding.apply {
            binding?.vModel = ItemCharacterViewModel(character)
            binding?.executePendingBindings()
        }

    }

    override fun onClick(v: View?) {
        delegate.onItemClick(character, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface Delegate {
        fun onItemClick(character: Character, view: View)
    }


}