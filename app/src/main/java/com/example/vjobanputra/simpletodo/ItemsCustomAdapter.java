package com.example.vjobanputra.simpletodo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vjobanputra on 8/16/15.
 */

public class ItemsCustomAdapter extends ArrayAdapter<Item> {
    public ItemsCustomAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_linear, parent, false);
        }

        TextView tvItemText = (TextView) convertView.findViewById(R.id.itemText);
        TextView tvItemDueDate = (TextView) convertView.findViewById(R.id.itemDueDate);
        TextView tvItemPriority = (TextView) convertView.findViewById(R.id.itemPriority);

        tvItemText.setText(item.getText());
        tvItemDueDate.setText(item.getDueDate());
        tvItemPriority.setText(item.getPriority());
        if (item.getDone() == true) {
            tvItemText.setPaintFlags(tvItemText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvItemDueDate.setPaintFlags(tvItemDueDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvItemPriority.setPaintFlags(tvItemPriority.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvItemText.setPaintFlags(tvItemText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            tvItemDueDate.setPaintFlags(0);
            tvItemPriority.setPaintFlags(0);
        }

        return convertView;
    }
}