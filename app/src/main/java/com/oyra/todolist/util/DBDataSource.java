package com.oyra.todolist.util;

import android.content.Context;

import com.oyra.todolist.data.DaoSession;
import com.oyra.todolist.data.Item;
import com.oyra.todolist.data.ItemDao;

import java.util.List;

import de.greenrobot.dao.async.AsyncSession;

/**
 * Works with the DB
 */

public class DBDataSource implements IDataSource {

    private AsyncSession mAsyncSession;
    private DaoSession mSession;
    private IDataListener mListener;

    public DBDataSource(Context context, IDataListener listener) {
        mListener = listener;
        if (context == null) {
            return;
        }
        mSession = SessionManager.getSession(context);
        mAsyncSession = SessionManager.getAsyncSession(context);
    }

    @Override
    public void getItems(boolean completed) {
        List res = mSession.getItemDao().queryBuilder()
                .where(ItemDao.Properties.IsComplete.eq(completed))
                .orderDesc(ItemDao.Properties.Timestamp)
                .list();
        if (mListener != null) {
            mListener.onItemsReceived(res, completed);
        }
    }

    @Override
    public void addItem(Item item) {
        mAsyncSession.insert(item);
    }

    @Override
    public void updateItem(Item item) {
        mAsyncSession.update(item);
    }

    @Override
    public void removeItem(Item item) {
        mAsyncSession.delete(item);
    }


    @Override
    public void close() {
        mAsyncSession = null;
        mSession.clear();
    }


}
