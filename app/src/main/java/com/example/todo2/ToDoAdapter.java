package com.example.todo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<ToDoItem> {

    public ToDoAdapter(Context context, int resource, List<ToDoItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item_layout, parent, false);
        }

        // Get the data item for this position
        ToDoItem toDoItem = getItem(position);

        // Lookup view for data population
        TextView taskTextView = convertView.findViewById(R.id.taskTextView);

        // Populate the data into the template view using the data object
        taskTextView.setText(toDoItem.getTask());

        // Return the completed view to render on screen
        return convertView;
    }
}
