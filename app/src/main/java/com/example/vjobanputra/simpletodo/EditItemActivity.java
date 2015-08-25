package com.example.vjobanputra.simpletodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class EditItemActivity extends ActionBarActivity implements PriorityAlertDialog.AlertPositiveListener {

    EditText etItem;
    EditText etDate;
    EditText etPriority;
    CheckBox cbDone;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItem = (EditText) findViewById(R.id.etItem);
        etItem.setText(getIntent().getStringExtra("itemText"));
        etItem.setSelection(etItem.getText().length());

        etDate = (EditText) findViewById(R.id.etDate);
        etDate.setHint(getIntent().getStringExtra("itemDueDate"));
        etDate.setSelection(etDate.getText().length());

        etPriority = (EditText) findViewById(R.id.etPriority);
        etPriority.setHint(getIntent().getStringExtra("itemPriority"));
        etPriority.setSelection(etPriority.getText().length());

        cbDone = (CheckBox) findViewById(R.id.cbDone);
        cbDone.setChecked(getIntent().getBooleanExtra("itemDone", false));

        setupDatePickerDialog();
        setupListViewListeners();
    }

    private void setupDatePickerDialog() {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setHint(new SimpleDateFormat("MM/dd/yyyy").format(newDate.getTime()));
            }
        }, year, month, day);
    }

    private void setupListViewListeners() {
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        etPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                String title = "Set Priority";
                int position = Priority.getPosition(etPriority.getHint().toString());

                PriorityAlertDialog alertDialog = PriorityAlertDialog.newInstance(title, position);
                alertDialog.show(fm, "fragment_alert");
            }
        });
    }

    @Override
    public void onPositiveClick(int position) {
        etPriority.setHint(Priority.options[position]);
    }

    public void saveItem(View view) {
        Intent data = new Intent();
        data.putExtra("itemText", etItem.getText().toString());
        data.putExtra("itemDueDate", etDate.getHint().toString());
        data.putExtra("itemPriority", etPriority.getHint().toString());
        data.putExtra("itemDone", cbDone.isChecked());
        data.putExtra("itemPos", getIntent().getIntExtra("itemPos", -1));
        setResult(RESULT_OK, data);
        finish();
    }
}
