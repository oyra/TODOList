package com.oyra.todolist.util;

import java.util.List;

public interface IDataListener {

    void onItemsReceived(List items, boolean complete);
}
