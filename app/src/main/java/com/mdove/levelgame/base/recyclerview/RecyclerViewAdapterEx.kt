package com.ss.android.uilib.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * 封装了EmptyView，HeaderView，FooterView的Adapter
 * 因为封装所以此处有两种position
 * 1. listPosition 在数据list中的位置
 * 2. adapterPosition 在LayoutManager中的位置
 *
 * 如设置了headerView的第一个Bean，listPosition为0，但是adapterPosition为1
 */
abstract class RecyclerViewAdapterEx<VH : RecyclerView.ViewHolder, Bean : Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_EMPTY = ViewHolderProducer.VIEW_TYPE_EMPTY
        private const val TYPE_HEADER = ViewHolderProducer.VIEW_TYPE_HEADER
        private const val TYPE_FOOTER = ViewHolderProducer.VIEW_TYPE_FOOTER
    }

    private var mEmptyViewProducer: ViewHolderProducer? = null
    private var mHeaderViewProducer: ViewHolderProducer? = null
    private var mFooterViewProducer: ViewHolderProducer? = null
    private var mIsEmpty: Boolean = false

    abstract val list: MutableList<Bean>

    /**
     * equivalent to [.getItemViewType]
     *
     * @param listPosition
     * @return
     */
    open fun getViewType(listPosition: Int) = 0

    /**
     * equivalent to [.onCreateViewHolder]}
     *
     * @param parent
     * @param viewType
     * @return
     */
    abstract fun onCreateCustomizeViewHolder(parent: ViewGroup, viewType: Int): VH

    /**
     * equivalent to onBindViewHolder(RecyclerView.ViewHolder, int)
     *
     * @param holder
     * @param position
     */
    abstract fun onBindCustomizeViewHolder(holder: VH, position: Int)

    open fun onBindCustomizeViewHolder(holder: VH, position: Int, payloads: List<Any>) {
        onBindCustomizeViewHolder(holder, position)
    }

    fun getAdapterPosition(bean: Bean): Int {
        val listIndex = list.indexOf(bean)
        return if (listIndex >= 0 && mHeaderViewProducer != null) listIndex + 1 else listIndex
    }

    fun getBeanByAdapterPosition(adapterPosition: Int): Bean {
        return list[getListPosition(adapterPosition)]
    }

    fun getListPosition(adapterPosition: Int): Int {
        return if (mHeaderViewProducer != null) adapterPosition - 1 else adapterPosition
    }


    /**
     * set empty view
     *
     * @param emptyViewProducer see [ViewProducer]
     */
    fun setEmptyViewProducer(emptyViewProducer: ViewHolderProducer) {
        if (mEmptyViewProducer !== emptyViewProducer) {
            mEmptyViewProducer = emptyViewProducer
            if (mIsEmpty) {
                notifyDataSetChanged()
            }
        }
    }

    /**
     * set Header view
     *
     * @param herderViewProducer see [ViewProducer]
     */
    fun setHeaderViewProducer(herderViewProducer: ViewHolderProducer) {
        if (mHeaderViewProducer !== herderViewProducer) {
            mHeaderViewProducer = herderViewProducer
            notifyItemInserted(0)
        }
    }

    fun updateHeaderViewProducer(headerViewProducer: ViewHolderProducer) {
        mHeaderViewProducer = headerViewProducer
        notifyItemChanged(0)
    }

    fun updateEmptyViewProducer(emptyViewProducer: ViewHolderProducer) {
        mEmptyViewProducer = emptyViewProducer
        notifyItemChanged(0)
    }

    fun updateFooterProducer(footerViewProducer: ViewHolderProducer?) {
        mFooterViewProducer = footerViewProducer
        notifyItemChanged(itemCount - 1)
    }

    /**
     * set Footer view
     *
     * @param footerViewProducer see [ViewProducer]
     */
    fun setFooterViewProducer(footerViewProducer: ViewHolderProducer) {
        if (mFooterViewProducer !== footerViewProducer) {
            mFooterViewProducer = footerViewProducer
            if (!mIsEmpty) {
                notifyItemInserted(itemCount - 1)
            }
        }
    }

    final override fun getItemViewType(position: Int): Int {
        if (mHeaderViewProducer != null && position == 0) {
            return TYPE_HEADER
        }
        if (mFooterViewProducer != null && position == itemCount - 1) {
            return TYPE_FOOTER
        }
        if (mIsEmpty) {
            return TYPE_EMPTY
        }
        val viewType = getViewType(if (mHeaderViewProducer != null) position - 1 else position)
        if (viewType != TYPE_EMPTY && viewType != TYPE_FOOTER && viewType != TYPE_HEADER) {
            return viewType
        } else {
            throw IllegalStateException("getViewType conflicts : $viewType")
        }
    }

    override fun getItemCount(): Int {
        var result = list.size
        if (result == 0 && mEmptyViewProducer != null) {
            mIsEmpty = true
            return if (mHeaderViewProducer == null) 1 else 2
        }
        if (mHeaderViewProducer != null) {
            result++
        }
        if (mFooterViewProducer != null) {
            result++
        }
        mIsEmpty = false
        return result
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EMPTY -> mEmptyViewProducer!!.onCreateViewHolder(parent)
            TYPE_HEADER -> mHeaderViewProducer!!.onCreateViewHolder(parent)
            TYPE_FOOTER -> mFooterViewProducer!!.onCreateViewHolder(parent)
            else -> onCreateCustomizeViewHolder(parent, viewType)
        }
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_EMPTY -> mEmptyViewProducer!!.onBindViewHolder(holder)
            TYPE_HEADER -> mHeaderViewProducer!!.onBindViewHolder(holder)
            TYPE_FOOTER -> mFooterViewProducer!!.onBindViewHolder(holder)
            else -> {
                onBindCustomizeViewHolder(holder as VH, position)
            }
        }
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        when (holder.itemViewType) {
            TYPE_EMPTY -> mEmptyViewProducer!!.onBindViewHolder(holder, payloads)
            TYPE_HEADER -> mHeaderViewProducer!!.onBindViewHolder(holder, payloads)
            TYPE_FOOTER -> mFooterViewProducer!!.onBindViewHolder(holder, payloads)
            else -> {
                onBindCustomizeViewHolder(holder as VH, position, payloads)
            }
        }
    }

}