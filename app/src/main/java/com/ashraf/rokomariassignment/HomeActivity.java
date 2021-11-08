package com.ashraf.rokomariassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ashraf.rokomariassignment.adapters.ToDoAdapter;
import com.ashraf.rokomariassignment.adapters.ViewPagerAdapter;
import com.ashraf.rokomariassignment.fragments.TaskListFragment;
import com.ashraf.rokomariassignment.model.ToDoModel;
import com.ashraf.rokomariassignment.utils.Constants;
import com.ashraf.rokomariassignment.utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab;
    ViewPager pager;
    ViewPagerAdapter pageAdapter;
    ImageButton btnOpen, btnInProgress,btnTest ,btnDone;
    int currentViewIndex = 0;
    boolean addNewFragment = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        pager = findViewById(R.id.viewpager);
        btnOpen=(ImageButton)findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(this);
        btnInProgress=(ImageButton)findViewById(R.id.btnInProgress);
        btnInProgress.setOnClickListener(this);
        btnTest=(ImageButton)findViewById(R.id.btnTest);
        btnTest.setOnClickListener(this);
        btnDone=(ImageButton)findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

    private void initializePagerAdapter()
    {
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new TaskListFragment(Constants.STATUS_OPEN));
        fragments.add(new TaskListFragment(Constants.STATUS_IN_PROGRESS));
        fragments.add(new TaskListFragment(Constants.STATUS_TEST));
        fragments.add(new TaskListFragment(Constants.STATUS_DONE));
        pageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pageAdapter);
    }

    private void selectCurrentTab(int imageViewIndex)
    {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpen:
                currentViewIndex = 0;
                pager.setCurrentItem(1,true);
                //selectCurrentTab(btn_visit_report, btn_visit_entry);
                break;
            case R.id.btnInProgress:
                currentViewIndex = 1;
                pager.setCurrentItem(1,true);
                //selectCurrentTab(btn_visit_report, btn_visit_entry);
                break;
            case R.id.btnTest:
                currentViewIndex = 2;
                pager.setCurrentItem(1,true);
                //selectCurrentTab(btn_visit_report, btn_visit_entry);
                break;
            case R.id.btnDone:
                currentViewIndex = 3;
                pager.setCurrentItem(1,true);
                //selectCurrentTab(btn_visit_report, btn_visit_entry);
                break;
            case R.id.fab:
                Intent intent=new Intent(HomeActivity.this, TaskManageActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}