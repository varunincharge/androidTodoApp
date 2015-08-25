package com.example.vjobanputra.simpletodo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoActivity extends FragmentActivity {

    ListView lvItems;
    EditText etNewItem;
    ArrayList<Item> arrayOfItems;
    ItemsCustomAdapter itemsAdapter;
    private final int REQUEST_CODE = 20;
    TodoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        dbHelper = TodoDbHelper.getInstance(this);
        arrayOfItems = (ArrayList<Item>)dbHelper.readItemsFromDb();
        itemsAdapter = new ItemsCustomAdapter(getBaseContext(), arrayOfItems);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListeners();
    }

    private void setupListViewListeners() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                arrayOfItems.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                dbHelper.writeItemsToDb(arrayOfItems);
                Toast.makeText(getBaseContext(), "Item removed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                i.putExtra("itemText", arrayOfItems.get(pos).getText());
                i.putExtra("itemDueDate", arrayOfItems.get(pos).getDueDate());
                i.putExtra("itemPriority", arrayOfItems.get(pos).getPriority());
                i.putExtra("itemDone", arrayOfItems.get(pos).getDone());
                i.putExtra("itemPos", pos);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String itemText = data.getExtras().getString("itemText");
            String itemDueDate = data.getExtras().getString("itemDueDate");
            String itemPriority = data.getExtras().getString("itemPriority");
            boolean itemDone = data.getExtras().getBoolean("itemDone");
            int itemPos = data.getExtras().getInt("itemPos");
            Item i = new Item (itemText, itemDueDate, itemPriority, itemDone);
            arrayOfItems.set(itemPos, i);
            itemsAdapter.notifyDataSetChanged();
            dbHelper.writeItemsToDb(arrayOfItems);
        }
    }

    public void addItem(View view) {
        String itemText = etNewItem.getText().toString();
        Item i = new Item (itemText);
        arrayOfItems.add(i);
        itemsAdapter.notifyDataSetChanged();
        dbHelper.writeItemsToDb(arrayOfItems);
        etNewItem.setText("");
    }
}
