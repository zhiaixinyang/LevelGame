package com.mdove.levelgame.base.recyclerview.checkeable

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 支持选择的RecyclerView#Adapter
 * 封装了选择、反选逻辑
 * 通过PAYLOAD进行局部刷新
 * 通过CheckStatusChangeInterceptor设置监听
 */
abstract class CheckableRecViewAdapter<VH : CheckableViewHolder, Bean : Any>(private val maxCheckNum: Int = 1) : RecyclerViewAdapterEx<VH, Bean>() {
    companion object {
        val PAYLOAD_CHECK = Any()
    }

    val checkedSet: MutableSet<Bean> = mutableSetOf()

    val checkedCount: Int
        get() = checkedSet.size

    var checkStatusChangeInterceptor: CheckStatusChangeInterceptor<Bean>? = null

    fun toggle(bean: Bean) {
        if (checkedSet.contains(bean)) {
            uncheck(bean)
        } else {
            check(bean)
        }
    }

    @JvmOverloads
    fun uncheck(bean: Bean, notifyCallBack: Boolean = true) {
        if (checkedSet.remove(bean)) {
            notifyItemCheckStatusChange(bean)
        }
        if (notifyCallBack) {
            checkStatusChangeInterceptor?.onCheckStatusChanged(checkedSet)
        }
    }

    fun check(bean: Bean, notifyCallBack: Boolean = true) {
        if (maxCheckNum == 1 && checkedSet.isNotEmpty()) {
            val setToUncheck = mutableSetOf<Bean>()
            setToUncheck.addAll(checkedSet)
            setToUncheck.forEach {
                uncheck(it, false)
            }
        }
        if (checkedSet.add(bean)) {
            notifyItemCheckStatusChange(bean)
        }
        if (notifyCallBack) {
            checkStatusChangeInterceptor?.onCheckStatusChanged(checkedSet)
        }
    }

    private fun notifyItemCheckStatusChange(bean: Bean) {
        getAdapterPosition(bean).apply {
            if (this >= 0) {
                notifyItemChanged(this, PAYLOAD_CHECK)
            }
        }
    }

    fun isChecked(bean: Bean): Boolean {
        return checkedSet.contains(bean)
    }

    @CallSuper
    override fun onBindCustomizeViewHolder(holder: VH, position: Int) {
        val bean = getBeanByAdapterPosition(position)
        holder.setChecked(isChecked(bean))
        holder.getCheckableRegion().setOnClickListener {
            val targetStatus = !isChecked(bean)
            val interceptor = checkStatusChangeInterceptor
            if (interceptor == null || !interceptor.onInterceptCheckStatus(bean, targetStatus, checkedSet, maxCheckNum)) {
                if (targetStatus) check(bean) else uncheck(bean)
            }
        }
    }

    @CallSuper
    override fun onBindCustomizeViewHolder(holder: VH, position: Int, payloads: List<Any>) {
        if (payloads.contains(PAYLOAD_CHECK) && payloads.size == 1) {
            val bean = getBeanByAdapterPosition(position)
            holder.setChecked(isChecked(bean))
        } else {
            onBindCustomizeViewHolder(holder, position)
        }
    }

}

abstract class CheckableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun setChecked(checked: Boolean)

    open fun getCheckableRegion(): View = itemView
}

interface CheckStatusChangeInterceptor<Bean : Any> {
    fun onInterceptCheckStatus(bean: Bean, checked: Boolean, checkedSet: Set<Bean>, maxCheckNum: Int): Boolean = false

    fun onCheckStatusChanged(checkedSet: Set<Bean>)
}