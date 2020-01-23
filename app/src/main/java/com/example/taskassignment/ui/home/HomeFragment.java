package com.example.taskassignment.ui.home;

import android.content.Intent;
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

import com.example.taskassignment.CreateNewTaskActivity;
import com.example.taskassignment.EmployeeActivity;
import com.example.taskassignment.MyAdapter;
import com.example.taskassignment.R;
import com.example.taskassignment.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private RecyclerView recyclerView;
    private MyAdapter homeAdapter;
    private LinearLayoutManager linearLayoutManager;
    List<Task> homeTasks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("tasks");

        recyclerView = root.findViewById(R.id.homeRecyclerView);
        setHomeTasks();
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton newTask = root.findViewById(R.id.fab_new_task);
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateNewTaskActivity.class);
                startActivity(intent);
            }
        });

        if (getActivity() instanceof EmployeeActivity) {
            newTask.hide();
        }
        return root;
    }

    public void setHomeTasks() {
        homeTasks = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                homeTasks.clear();
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    Task task = dataSnapshot.child(String.valueOf(i)).getValue(Task.class);
                    homeTasks.add(task);
                    homeAdapter = new MyAdapter(homeTasks);
                    recyclerView.setAdapter(homeAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }
}