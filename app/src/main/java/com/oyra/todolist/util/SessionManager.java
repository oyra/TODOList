package com.oyra.todolist.util;

import android.content.Context;

import com.oyra.todolist.data.DaoMaster;
import com.oyra.todolist.data.DaoSession;

import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by oyra on 21/01/16.
 */
class SessionManager {

    private static DaoSession mSession = null;
    private static final String DB_NAME = "todolist";

    public static DaoSession getSession(Context context) {

        if (mSession == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            mSession = daoMaster.newSession();
        }
        return mSession;
    }

    public static AsyncSession getAsyncSession(Context context) {
        return getSession(context).startAsyncSession();
    }

}
