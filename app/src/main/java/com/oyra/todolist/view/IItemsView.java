package com.oyra.todolist.view;

import android.content.Context;

import com.oyra.todolist.data.Item;

import java.util.List;

/**
 * Created by oyra on 20/01/16.
 */
public interface IItemsView {

    Context getContext();

    void showItems(List<Item> items, boolean complete);

    void addItem(Item item);

    void removeItem(Item item, int position);

    void updateItem(Item item, int position);

}
