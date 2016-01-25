package com.oyra.todolist.util;

import com.oyra.todolist.data.Item;

/**
 * Created by oyra on 21/01/16.
 */
public interface IDataSource {

    void getItems(boolean completed);

    void addItem(Item item);

    void updateItem(Item item);

    void removeItem(Item item);

    void close();
}
