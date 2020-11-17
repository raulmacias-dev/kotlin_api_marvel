package com.raul.macias.wikiheroes.view.viewholder

import androidx.databinding.DataBindingUtil
import android.view.View
import com.raul.macias.wikiheroes.databinding.ItemCharacterBinding
import com.raul.macias.wikiheroes.models.Character
import com.raul.macias.wikiheroes.view.ui.home.ItemCharacterViewModel

class CharacterViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    //here we define actions that we handle
    interface Delegate {
        fun onItemClick(character: Character, view: View)
    }

    private lateinit var character: Character

    private val binding by lazy { DataBindingUtil.bind<ItemCharacterBinding>(view) }


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

    override fun onClick(view: View?) {
        delegate.onItemClick(character, itemView)
    }

    override fun onLongClick(view: View?): Boolean {
        return false
    }


}