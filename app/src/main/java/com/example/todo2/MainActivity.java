package com.example.todo2;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ToDoAdapter todoAdapter;
    private ArrayList<ToDoItem> todoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a list of TodoItems
        todoItems = new ArrayList<>();
        todoItems.add(new ToDoItem("Task 1"));
        todoItems.add(new ToDoItem("Task 2"));

        // Create the adapter and set it to the ListView
        todoAdapter = new ToDoAdapter(this, R.layout.todo_item_layout, todoItems);
        ListView listView = findViewById(R.id.itemlistview);
        listView.setAdapter(todoAdapter);

        // Setup remove listener method call
        setupListViewListener();

        // Read items from the file
        readItems();
    }

    public void onAddItem(View v) {
        EditText eNewItem = findViewById(R.id.newitem);
        String itemText = eNewItem.getText().toString();

        // Add a new ToDoItem to the adapter
        ToDoItem newToDoItem = new ToDoItem(itemText);
        todoAdapter.add(newToDoItem);

        // Clear the EditText
        eNewItem.setText("");

        // Notify the adapter that the dataset has changed
        todoAdapter.notifyDataSetChanged();

        // Write items to the file
        writeItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Attaches a long click listener to the listview
    private void setupListViewListener() {
        ListView listView = findViewById(R.id.itemlistview);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                // Remove the item within the adapter at position
                todoAdapter.remove(todoAdapter.getItem(pos));
                // Notify the adapter that the dataset has changed
                todoAdapter.notifyDataSetChanged();
                // Write items to the file
                writeItems();
                // Return true consumes the long click event (marks it handled)
                return true;
            }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            // Read lines from the file and populate the todoItems list
            ArrayList<String> lines = new ArrayList<>(FileUtils.readLines(todoFile));
            todoItems.clear(); // Clear the existing items
            for (String line : lines) {
                todoItems.add(new ToDoItem(line));
            }
            todoAdapter.notifyDataSetChanged(); // Notify the adapter of changes
        } catch (IOException e) {
            todoItems = new ArrayList<>(); // If there's an exception, initialize an empty list
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            // Write lines to the file based on the todoItems list
            ArrayList<String> lines = new ArrayList<>();
            for (ToDoItem todoItem : todoItems) {
                lines.add(todoItem.getTask());
            }
            FileUtils.writeLines(todoFile, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
