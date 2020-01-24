package com.example.taskassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView showTask;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.showTask = itemView.findViewById(R.id.task);
        }
    }

    private List<Task> myTasks;

    public MyAdapter(List<Task> myTasks) {
        this.myTasks = myTasks;
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
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        holder.showTask.setText(myTasks.get(position).taskName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the functionality
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTasks.size();
    }
}