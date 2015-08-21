package com.example.ale.todolist;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;


@Table(name = "tasks")
public class Task extends Model {

    @Column(name = "task")
    private String task;

    public Task() {
        super();
    }

    public void saveTask(){
        save();
    }


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void deleteTask(){
        long i = getId();
        new Delete().from(Task.class).where("Id = ?", i).execute();
    }


}
