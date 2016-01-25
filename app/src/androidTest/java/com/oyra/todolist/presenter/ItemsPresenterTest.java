package com.oyra.todolist.presenter;

import com.oyra.todolist.data.Item;
import com.oyra.todolist.presenter.stubs.FakeItemsView;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oyra on 21/01/16.
 */
public class ItemsPresenterTest extends TestCase {

    public void testGetItems() throws Exception {

    }

    public void testOnItemsReceived() throws Exception {
        FakeItemsView view = new FakeItemsView();
        ItemsPresenter p = new ItemsPresenter(view);

        List<Item> items = new ArrayList<>();
        Item i = new Item();
        items.add(i);
        p.onItemsReceived(items, true);

        assertEquals(view.getItemsQuantity(), items.size());
    }

    public void testAddItem() throws Exception {

    }

    public void testRemoveItem() throws Exception {

    }

    public void testUpdateItem() throws Exception {

    }
}