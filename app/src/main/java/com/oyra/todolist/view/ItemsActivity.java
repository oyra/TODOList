package com.oyra.todolist.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.oyra.todolist.IActionsListener;
import com.oyra.todolist.R;
import com.oyra.todolist.adapter.ItemsListAdapter;
import com.oyra.todolist.data.Item;
import com.oyra.todolist.presenter.IItemsPresenter;
import com.oyra.todolist.presenter.ItemsPresenter;

import java.util.List;

public class ItemsActivity extends AppCompatActivity implements IItemsView, IActionsListener {


    private IItemsPresenter mPresenter;
    private ItemsListAdapter mAdapter;
    private RecyclerView mView;
    private FloatingActionButton mAddButton;
    private TextView mInfoText;
    private TextView mTitleText;
    //the default is showing active (incomplete) tasks
    private final boolean mShowCompleteTasksByDefault = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        colorStatusBar();

        mPresenter = new ItemsPresenter(this);
        init();

        mPresenter.getItems(mShowCompleteTasksByDefault);
        mPresenter.getItems(!mShowCompleteTasksByDefault);
        setAddButtonVisibility();
        setTitle();

    }

    private void showAddItemDialog() {
        AddItemDialog d = new AddItemDialog();
        FragmentManager fm = getSupportFragmentManager();
        d.setListener(this);
        d.show(fm, "add_city_dialog");
    }


    private void init() {

        mInfoText = (TextView) findViewById(R.id.info);
        mTitleText = (TextView) findViewById(R.id.title);
        CheckBox check = (CheckBox) findViewById(R.id.lists_switch);
        mAddButton = (FloatingActionButton) findViewById(R.id.fab);
        mView = (RecyclerView) findViewById(R.id.items_list);

        mView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mView.setLayoutManager(mLayoutManager);
        mAdapter = new ItemsListAdapter();
        mAdapter.setShowComplete(mShowCompleteTasksByDefault);
        mView.setAdapter(mAdapter);


        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //switch lists
                mAdapter.switchLists();
                setAddButtonVisibility();
                setTitle();
            }
        });

        initSwipes();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();

            }
        });


    }

    private void initSwipes() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                Item item = mAdapter.getItemAt(viewHolder.getAdapterPosition());
                if (item.getIsComplete()) {
                    //may be removed
                    if (swipeDir == ItemTouchHelper.LEFT || swipeDir == ItemTouchHelper.START) {
                        //make incomplete
                        switchCompletionStatus(item, viewHolder.getAdapterPosition());
                        showConfirmation(R.string.restored);
                    } else {
                        //remove
                        remove(item, viewHolder.getAdapterPosition());
                        showConfirmation(R.string.removed);

                    }
                    return;
                }
                switchCompletionStatus(item, viewHolder.getAdapterPosition());
                showConfirmation(R.string.archived);
            }

            private void switchCompletionStatus(Item item, int position) {
                item.setIsComplete(!item.getIsComplete());
                item.setTimestamp(System.currentTimeMillis());

                mPresenter.updateItem(item, position);
            }

            private void remove(Item item, int position) {
                mPresenter.removeItem(item, position);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.START | ItemTouchHelper.END);
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mView);

    }

    private void colorStatusBar() {
        if (Build.VERSION.SDK_INT > 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }


    @Override
    public void showItems(List<Item> items, boolean complete) {
        mAdapter.addAll(items, complete);
        setInfoTextVisibility();
    }

    @Override
    public void addItem(Item item) {
        mAdapter.add(item);
        setInfoTextVisibility();
    }

    private void setInfoTextVisibility() {
        mInfoText.setVisibility(mAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }

    private void setAddButtonVisibility() {
        mAddButton.setVisibility(mAdapter.isShowComplete() ? View.GONE : View.VISIBLE);
    }

    private void setTitle() {
        mTitleText.setText(mAdapter.isShowComplete() ? R.string.archive : R.string.active);
    }

    private void showConfirmation(int textToShow) {
        Snackbar.make(mView, textToShow, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void removeItem(Item item, int position) {
        mAdapter.remove(position, item.getIsComplete());
    }

    @Override
    public void updateItem(Item item, int position) {
        mAdapter.updateItemAt(position, item);
    }

    @Override
    public void onItemAdd(Item item) {
        mPresenter.addItem(item);
    }
}
