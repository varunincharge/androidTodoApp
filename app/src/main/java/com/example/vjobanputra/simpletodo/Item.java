package com.example.vjobanputra.simpletodo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by vjobanputra on 8/16/15.
 *
 * Represents a single Todo Item.
 */
public class Item {
    private String text;
    private boolean done;
    private String dueDate;
    private String priority;

    public Item(String text) {
        this.text = text;
        this.done = false;
        this.dueDate = getDefaultDueDate();
        this.priority = "Medium";
    }

    public Item(String text, String dueDate, String priority, boolean done) {
        this.text = text;
        this.done = done;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    private String getDefaultDueDate() {

        // Get today's date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day);

        return new SimpleDateFormat("MM/dd/yyyy").format(newDate.getTime());
    }

    public String getText() {
        return this.text;
    }
    public String getDueDate() {
        return this.dueDate;
    }
    public String getPriority() { return this.priority; }
    public boolean getDone() { return this.done; }
}
