package com.example.taskassignment.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskassignment.ManagerActivity;
import com.example.taskassignment.MyAdapter;
import com.example.taskassignment.R;
import com.example.taskassignment.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private RecyclerView recyclerView;
    private MyAdapter completedAdapter;
    private LinearLayoutManager linearLayoutManager;
    List<Task> completedTasks;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("tasks");

        recyclerView = root.findViewById(R.id.completedRecyclerView);
        setApprovedTasks();
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }

    public void setApprovedTasks() {
        completedTasks = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                completedTasks.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = snapshot.getValue(Task.class);
                    if (task != null) {
                        task.setTaskID(snapshot.getKey());
                        if(getActivity() instanceof ManagerActivity) {
                            if (task.createdByUser.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                            }
                            else continue;
                        }
                        else {
                            if (task.assignedToUser.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                            } else continue;
                        }
                        if(task.taskStatus.equalsIgnoreCase("completed")) {
                            completedTasks.add(task);
                        }
                    }
                    completedAdapter = new MyAdapter(completedTasks, "Completed");
                    recyclerView.setAdapter(completedAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }
}