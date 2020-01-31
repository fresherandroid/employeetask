package com.example.taskassignment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView showTask;
        private TextView taskCreated;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.showTask = itemView.findViewById(R.id.task);
            this.taskCreated = itemView.findViewById(R.id.task_created);
        }
    }

    private List<Task> myTasks;
    private String fragment;

    public MyAdapter(List<Task> myTasks, String fragment) {
        this.myTasks = myTasks;
        this.fragment = fragment;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.showTask.setText(myTasks.get(position).taskName);
        holder.taskCreated.setText(myTasks.get(position).timeStampTask);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the functionality
                if (v.getContext() instanceof EmployeeActivity) {
                    Intent intent = new Intent(v.getContext(), RequestTaskActivity.class);
                    intent.putExtra("taskId", myTasks.get(holder.getAdapterPosition()).taskID);
                    intent.putExtra("fragment", fragment);
                    v.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), ApproveTaskActivity.class);
                    intent.putExtra("taskId", myTasks.get(holder.getAdapterPosition()).taskID);
                    intent.putExtra("fragment", fragment);
                    v.getContext().startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return myTasks.size();
    }
}