package com.ashraf.rokomariassignment.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashraf.rokomariassignment.HomeActivity;
import com.ashraf.rokomariassignment.R;
import com.ashraf.rokomariassignment.TaskManageActivity;
import com.ashraf.rokomariassignment.adapters.ToDoAdapter;
import com.ashraf.rokomariassignment.model.ToDoModel;
import com.ashraf.rokomariassignment.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TaskListFragment extends Fragment {
    private DatabaseHandler db;
     RecyclerView tasksRecyclerView;
     ToDoAdapter tasksAdapter;
    ArrayList<ToDoModel> taskList;
    String title;


    public TaskListFragment(String title) {
        this.title=title;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(getContext());
        db.openDatabase();
        taskList = db.getAllTasksByStatus(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_task_list, container, false);
        tasksRecyclerView = rootView.findViewById(R.id.tasksRecyclerView);
        tasksAdapter = new ToDoAdapter(getContext(),taskList);
        tasksRecyclerView.setAdapter(tasksAdapter);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }
}