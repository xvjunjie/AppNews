package com.android.baselibrary.adapter;

import android.content.Context;

import com.android.baselibrary.adapter.base.ItemViewDelegate;

import java.util.List;

/**
 * Adapter for ListView,RecyclerView,GridView等，
 * 支持多种Item类型的情况。
 * @param <T>
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{

    public CommonAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            /**
             * 多种Item类型的时候使用，正常没使用到
             * @param item
             * @param position
             * @return
             */
            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
