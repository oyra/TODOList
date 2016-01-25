package com.oyra.todolist.presenter.stubs;

import android.content.Context;

import com.oyra.todolist.data.Item;
import com.oyra.todolist.view.IItemsView;

import java.util.List;

/**
 * Created by oyra on 22/01/16.
 */
public class FakeItemsView implements IItemsView {

    private int itemsQuantity = 0;

    public int getItemsQuantity() {
        return itemsQuantity;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showItems(List<Item> items, boolean complete) {
        if (items == null || items.size() == 0) {
            itemsQuantity = 0;
            return;
        }
        itemsQuantity = items.size();

    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void removeItem(Item item, int position) {

    }

    @Override
    public void updateItem(Item item, int position) {

    }
}
