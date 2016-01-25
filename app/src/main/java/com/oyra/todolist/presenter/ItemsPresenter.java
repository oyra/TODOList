package com.oyra.todolist.presenter;

import com.oyra.todolist.data.Item;
import com.oyra.todolist.util.DBDataSource;
import com.oyra.todolist.util.IDataListener;
import com.oyra.todolist.util.IDataSource;
import com.oyra.todolist.view.IItemsView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by oyra on 20/01/16.
 */
public class ItemsPresenter implements IItemsPresenter, IDataListener {

    private WeakReference<IItemsView> mView;
    private IDataSource mDataSource;

    public ItemsPresenter(IItemsView view) {
        mView = new WeakReference<>(view);

        init();
    }

    private void init() {
        if (mView.get() == null) {
            return;
        }
        mDataSource = new DBDataSource(mView.get().getContext(), this);
    }

    @Override
    public void getItems(boolean complete) {
        mDataSource.getItems(complete);
    }

    @Override
    public void onItemsReceived(List items, boolean complete) {
        if (mView.get() == null) {
            return;
        }
        mView.get().showItems(items, complete);
    }

    @Override
    public void addItem(Item item) {
        mDataSource.addItem(item);
        if (mView.get() == null) {
            return;
        }
        mView.get().addItem(item);
    }

    @Override
    public void removeItem(Item item, int position) {
        mDataSource.removeItem(item);
        if (mView.get() == null) {
            return;
        }
        mView.get().removeItem(item, position);
    }

    @Override
    public void updateItem(Item item, int position) {
        mDataSource.updateItem(item);
        if (mView.get() == null) {
            return;
        }
        mView.get().updateItem(item, position);
    }


}
