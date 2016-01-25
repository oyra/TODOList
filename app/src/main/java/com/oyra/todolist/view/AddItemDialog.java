package com.oyra.todolist.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.oyra.todolist.IActionsListener;
import com.oyra.todolist.R;
import com.oyra.todolist.data.Item;

import java.lang.ref.WeakReference;

/**
 * Created by oyra on 20/01/16.
 */
public class AddItemDialog extends DialogFragment {
    private AppCompatEditText mEditText;
    private TextInputLayout mWrapper;
    private WeakReference<IActionsListener> mListener;


    public void setListener(IActionsListener lstnr) {
        if (lstnr == null) {
            mListener = null;
        }
        mListener = new WeakReference<>(lstnr);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.add_item_dialog, null);
        mEditText = (AppCompatEditText) rootView.findViewById(R.id.item_name);
        mWrapper = (TextInputLayout) rootView.findViewById(R.id.item_name_wrapper);

        builder.setView(rootView);

        AlertDialog d = builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //to prevent the dismissing - a well-known Android bug
            }
        }).create();


        d.setCanceledOnTouchOutside(false);
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return d;
    }

    @Override
    public void onStart() {
        super.onStart();

        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            int color = ContextCompat.getColor(getContext(), R.color.colorAdditionalA);
            d.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);
            d.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color);
            d.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem();

                }
            });
        }
    }

    private void addItem() {
        if (mEditText.getText() != null && mEditText.getText().toString().trim().length() > 0) {
            if (mListener != null && mListener.get() != null) {
                Item item = new Item();
                item.setText(mEditText.getText().toString().trim());
                item.setTimestamp(System.currentTimeMillis());
                mListener.get().onItemAdd(item);
            }
            dismiss();
        } else {
            mWrapper.setError(mEditText.getResources().getString(R.string.edit_error));
        }
    }

}
