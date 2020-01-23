package com.example.taskassignment;

public class Task {
    public String task;
    public String createdByUser;

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Task(String task, String createdByUser) {
        this.task = task;
        this.createdByUser = createdByUser;
    }
}
