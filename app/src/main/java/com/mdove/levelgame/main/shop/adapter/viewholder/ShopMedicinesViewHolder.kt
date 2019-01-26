package com.mdove.levelgame.main.shop.adapter.viewholder

import android.support.v7.widget.RecyclerView
import com.mdove.levelgame.databinding.ItemMedicinesBinding
import com.mdove.levelgame.main.shop.model.handler.MedicinesItemHandler
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesPresenter

/**
 * Created by MDove on 2018/12/23.
 */
class ShopMedicinesViewHolder(private val binding: ItemMedicinesBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(vm: MedicinesModelVM, presenter: ShopMedicinesPresenter) {
        binding.vm = vm
        binding.handler = MedicinesItemHandler(presenter)
    }

    fun refreshCount(vm: MedicinesModelVM, presenter: ShopMedicinesPresenter) {
        binding.tvLimit.text = vm.limitCount.get()
        binding.handler = MedicinesItemHandler(presenter)
    }
}
