package com.example.ale.todolist;


import com.activeandroid.ActiveAndroid;

public class MyApplication extends com.activeandroid.app.Application {

    public void onCreate(){
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
