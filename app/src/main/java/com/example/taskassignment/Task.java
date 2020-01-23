package com.example.taskassignment;

public class Task {
    public String taskName;
    public String createdByUser;

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Task(String taskName, String createdByUser) {
        this.taskName = taskName;
        this.createdByUser = createdByUser;
    }
}
