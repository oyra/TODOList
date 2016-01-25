package com.oyra.todolist.presenter;

import com.oyra.todolist.data.Item;

/**
 * Created by oyra on 20/01/16.
 */
public interface IItemsPresenter {

    void getItems(boolean complete);

    void addItem(Item item);

    void removeItem(Item item, int position);

    void updateItem(Item item, int position);

}
