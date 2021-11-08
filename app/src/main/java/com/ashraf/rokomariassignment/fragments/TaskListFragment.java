package com.ashraf.rokomariassignment.fragments;

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

import java.util.Collections;
import java.util.List;


public class TaskListFragment extends Fragment {
    private DatabaseHandler db;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    String title;

    public TaskListFragment(String title) {
        this.title=title;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_task_list, container, false);

        db = new DatabaseHandler(getContext());
        db.openDatabase();

        tasksRecyclerView = rootView.findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksAdapter = new ToDoAdapter(db, getActivity());
        tasksRecyclerView.setAdapter(tasksAdapter);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);
        return rootView;
    }
}