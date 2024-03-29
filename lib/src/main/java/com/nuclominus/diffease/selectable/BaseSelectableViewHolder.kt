package com.nuclominus.diffease.selectable

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.viewbinding.ViewBinding
import com.nuclominus.diffease.base.BaseViewHolder
import com.nuclominus.diffease.base.ListObserver

abstract class BaseSelectableViewHolder<TModel, TKey>(
    binding: ViewBinding,
    override val callback: ListObserver<TModel>
) : BaseViewHolder<TModel>(binding), SelectableViewHolder<TKey> {

    open fun bind(current: TModel, isActivated: Boolean) {
        super.bind(current)
        itemView.isActivated = isActivated
    }

    protected abstract fun resolveKey(model: TModel): TKey?

    override fun itemDetails(): ItemDetailsLookup.ItemDetails<TKey>? {
        return model?.let {
            return object : ItemDetailsLookup.ItemDetails<TKey>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): TKey? = resolveKey(it)
                override fun inSelectionHotspot(e: MotionEvent) = true
            }
        }
    }
}