package com.oyra.todolist.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oyra.todolist.R;
import com.oyra.todolist.data.Item;

import java.util.LinkedList;
import java.util.List;

/**
 * Both complete & incomplete items adapter
 */
public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ViewHolder> {
    private LinkedList<Item> mListComplete;
    private LinkedList<Item> mListIncomplete;
    private int mColorComplete;
    private int mColorIncomplete;

    private boolean mShowComplete = false;


    public ItemsListAdapter() {
        mListComplete = new LinkedList<>();
        mListIncomplete = new LinkedList<>();
    }

    /**
     * Set the adapter to show completed/ incomplete items
     *
     * @param complete
     */
    public void setShowComplete(boolean complete) {
        mShowComplete = complete;
    }

    public boolean isShowComplete() {
        return mShowComplete;
    }

    public void switchLists() {
        mShowComplete = !mShowComplete;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        mColorComplete = ContextCompat.getColor(parent.getContext(), R.color.colorAdditionalA);
        mColorIncomplete = ContextCompat.getColor(parent.getContext(), R.color.colorAdditionalB);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (mShowComplete) {
            holder.mText.setText(mListComplete.get(position).getText());
            holder.mContainer.setCardBackgroundColor(mColorComplete);
        } else {
            holder.mText.setText(mListIncomplete.get(position).getText());
            holder.mContainer.setCardBackgroundColor(mColorIncomplete);
        }
    }

    @Override
    public int getItemCount() {
        if (mShowComplete) {
            return mListComplete.size();
        }
        return mListIncomplete.size();
    }

    public void add(Item item) {
        if (item.getIsComplete()) {
            mListComplete.addFirst(item);
            if (mShowComplete) {
                notifyItemInserted(0);
            }
        } else {
            mListIncomplete.addFirst(item);
            if (!mShowComplete) {
                notifyItemInserted(0);
            }
        }

    }

    public Item getItemAt(int pos) {
        return (mShowComplete ? mListComplete.get(pos) : mListIncomplete.get(pos));
    }

    public void updateItemAt(int position, Item newItem) {

        //if the completion status changed
        if (newItem.getIsComplete() != mShowComplete) {
            //move to the other list
            if (newItem.getIsComplete()) {
                mListIncomplete.remove(position);
                mListComplete.addFirst(newItem);
            } else {
                mListComplete.remove(position);
                mListIncomplete.addFirst(newItem);
            }
            notifyItemRemoved(position);
            return;
        }

        //just update
        if (newItem.getIsComplete()) {
            mListComplete.set(position, newItem);
        } else {
            mListIncomplete.set(position, newItem);
        }
        notifyItemChanged(position);

    }

    public void addAll(List<Item> items, boolean complete) {
        if (items == null) {
            return;
        }
        if (complete) {
            mListComplete.addAll(items);
        } else {
            mListIncomplete.addAll(items);
        }
    }

    public void remove(Item item) {
        if (item.getIsComplete()) {
            mListComplete.remove(item);
        } else {
            mListIncomplete.remove(item);
        }
        if (item.getIsComplete() == mShowComplete) {
            notifyDataSetChanged();
        }
    }

    public void remove(int position, boolean isCompleted) {
        if (isCompleted) {
            mListComplete.remove(position);
        } else {
            mListIncomplete.remove(position);
        }
        if (isCompleted == mShowComplete) {
            notifyItemRemoved(position);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;
        CardView mContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);
            mContainer = (CardView) itemView.findViewById(R.id.container);
        }
    }
}
