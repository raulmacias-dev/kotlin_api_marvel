package com.raul.macias.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.raul.macias.wikiheroes.databinding.CreatorRowBinding
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.view.adapter.CreatorsAdapter

class CreatorRowViewHolder(val view: View, val delegate: CreatorViewHolder.Delegate) : BaseViewHolder(view) {

    lateinit var row : Pair<String , List<Item>>

    private val binding by lazy { DataBindingUtil.bind<CreatorRowBinding>(view) }

    lateinit var adapterCreators : CreatorsAdapter

    var layoutManager = LinearLayoutManager( view.context , LinearLayoutManager.HORIZONTAL, false)

    override fun bindData(data: Any?) {
        if(data is Pair<*, *>) {
            row = data as Pair<String, List<Item>>
            drawUI()
        }
    }

    private fun drawUI() {

        adapterCreators = CreatorsAdapter(delegate , row.second)

        binding?.let {
            binding?.role?.text = row.first.capitalize() + " : "
            //init recycle view creator
            binding?.creatorsList?.layoutManager = layoutManager
            binding?.creatorsList?.adapter = adapterCreators
        }


    }

    override fun onClick(v: View?) {
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }
}