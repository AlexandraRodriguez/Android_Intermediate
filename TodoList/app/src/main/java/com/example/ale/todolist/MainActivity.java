package com.example.ale.todolist;


import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import com.activeandroid.query.Select;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    private TasksAdapter adapter;
    private List<Task> list;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.main);

        list = new ArrayList<Task>();
        list = getTasks();
        adapter = new TasksAdapter(this, list);
        this.setListAdapter(adapter);
    }

    public void addTask(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a task");
        builder.setMessage("What do you want to do?");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Task task = new Task();
                String txtTask = inputField.getText().toString();
                task.setTask(txtTask);
                task.saveTask();

                updateUI();
            }
        });

        builder.setNegativeButton("Cancel",null);
        builder.create().show();

    }


    public void updateUI() {
        List<Task> list = getTasks();
        adapter = new TasksAdapter(this, list);
        this.setListAdapter(adapter);
    }

    public List<Task> getTasks() {
        List<Task> taskList = new Select()
                .from(Task.class)
                .execute();
        return taskList;
    }




}
