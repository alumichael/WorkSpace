package com.mike4christ.tisvdigital;


import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemClickSupport {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private final RecyclerView mRecyclerView;

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (ItemClickSupport.this.mOnItemClickListener != null) {
                view.setOnClickListener(ItemClickSupport.this.mOnClickListener);
            }
            if (ItemClickSupport.this.mOnItemLongClickListener != null) {
                view.setOnLongClickListener(ItemClickSupport.this.mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ItemClickSupport.this.mOnItemClickListener != null) {
                ItemClickSupport.this.mOnItemClickListener.onItemClicked(ItemClickSupport.this.mRecyclerView, ItemClickSupport.this.mRecyclerView.getChildViewHolder(v).getAdapterPosition(), v);
            }
        }
    };

    private OnLongClickListener mOnLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (ItemClickSupport.this.mOnItemLongClickListener == null) {
                return false;
            }
            return ItemClickSupport.this.mOnItemLongClickListener.onItemLongClicked(ItemClickSupport.this.mRecyclerView, ItemClickSupport.this.mRecyclerView.getChildViewHolder(v).getAdapterPosition(), v);

        }
    };



    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int i, View view);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int i, View view);
    }

    private ItemClickSupport(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mRecyclerView.setTag(R.id.item_click_support, this);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this.mAttachListener);
    }

    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            return new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(this.mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }
}
