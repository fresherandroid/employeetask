package com.example.taskassignment;

public class Task {
    public String taskName;
    public String createdByUser;
    public String assignedToUser;
    public String taskStatus;
    public String timeStampTask;

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Task(String taskName, String createdByUser, String timeStampTask) {
        this.taskName = taskName;
        this.createdByUser = createdByUser;
        this.timeStampTask = timeStampTask;
    }
}
