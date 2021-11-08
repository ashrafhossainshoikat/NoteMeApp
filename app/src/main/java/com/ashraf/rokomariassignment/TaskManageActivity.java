package com.ashraf.rokomariassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ashraf.rokomariassignment.utils.Constants;
import com.ashraf.rokomariassignment.utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskManageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "ActionBottomDialog";
    private EditText etTaskName, etDescription;
    private TextView tvDeadLine;
    private Spinner spStatus;
    private Button btnSubmit;
    private ImageButton btnDateSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manage);
        getSupportActionBar().hide();
        initializeUIelements();

    }

    void initializeUIelements(){
        etTaskName=(EditText) findViewById(R.id.etTaskName);
        etDescription=(EditText) findViewById(R.id.etDescription);
        tvDeadLine=(TextView) findViewById(R.id.tvDeadLine);
        tvDeadLine.setText(Utility.getDateFromMillisecond(Calendar.getInstance().getTimeInMillis(), Constants.DATE_FORMAT_DD_MM_YYYY));

        spStatus=(Spinner) findViewById(R.id.spStatus);

        List<String> stausList = new ArrayList<String>();
        stausList.add(Constants.STATUS_OPEN);
        stausList.add(Constants.STATUS_IN_PROGRESS);
        stausList.add(Constants.STATUS_TEST);
        stausList.add(Constants.STATUS_DONE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
               R.layout.spinner_dropdown_item, stausList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spStatus.setAdapter(dataAdapter);

        btnDateSelect=(ImageButton) findViewById(R.id.btnDateSelect);
        btnDateSelect.setOnClickListener(this);
        btnSubmit=(Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}