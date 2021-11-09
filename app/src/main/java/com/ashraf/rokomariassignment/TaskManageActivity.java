package com.ashraf.rokomariassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ashraf.rokomariassignment.model.ToDoModel;
import com.ashraf.rokomariassignment.utils.Constants;
import com.ashraf.rokomariassignment.utils.DatabaseHandler;
import com.ashraf.rokomariassignment.utils.Utility;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskManageActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private DatabaseHandler db;
    private EditText etTaskName, etDescription;
    private TextView tvDeadLine,tvTitle;
    private Spinner spStatus;
    private Button btnSubmit;
    private ImageButton btnDateSelect, btnEmail, btnPhone, btnUrl;
    ToDoModel toDoModel=new ToDoModel();
    boolean isUpdate=false;

    Calendar deadlinesValue = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manage);
        getSupportActionBar().hide();
        initializeUIelements();
    }

    void initializeUIelements() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        etTaskName = (EditText) findViewById(R.id.etTaskName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        tvDeadLine = (TextView) findViewById(R.id.tvDeadLine);
        tvDeadLine.setText(Utility.getDateFromMillisecond(deadlinesValue.getTimeInMillis(), Constants.DATE_FORMAT_DD_MM_YYYY));

        spStatus = (Spinner) findViewById(R.id.spStatus);

        List<String> stausList = new ArrayList<String>();
        stausList.add(Constants.STATUS_OPEN);
        stausList.add(Constants.STATUS_IN_PROGRESS);
        stausList.add(Constants.STATUS_TEST);
        stausList.add(Constants.STATUS_DONE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_dropdown_item, stausList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spStatus.setAdapter(dataAdapter);

        btnDateSelect = (ImageButton) findViewById(R.id.btnDateSelect);
        btnDateSelect.setOnClickListener(this);

        btnEmail = (ImageButton) findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(this);

        btnPhone = (ImageButton) findViewById(R.id.btnPhone);
        btnPhone.setOnClickListener(this);

        btnUrl = (ImageButton) findViewById(R.id.btnUrl);
        btnUrl.setOnClickListener(this);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        if (b!=null && b.containsKey("isUpdate")) {
            isUpdate = this.getIntent().getExtras().getBoolean("isUpdate");
            toDoModel=(ToDoModel) this.getIntent().getSerializableExtra("todoTask");
        }else {
            isUpdate=false;
            toDoModel=new ToDoModel();
        }

        if(isUpdate){
            tvTitle.setText(this.getString(R.string.title_edit_task));
            etTaskName.setText(toDoModel.getTaskName());
            etDescription.setText(toDoModel.getDescription());
            spStatus.setSelection(((ArrayAdapter<String>)spStatus.getAdapter()).getPosition(toDoModel.getStatus()));
            tvDeadLine.setText(toDoModel.getDeadline());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDateSelect:
                DatePickerDialog dpd = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                deadlinesValue.set(year, monthOfYear, dayOfMonth, 0, 0);
                                tvDeadLine.setText(Constants.DATE_FORMAT_DD_MM_YYYY.format(deadlinesValue.getTime()));

                            }
                        }, deadlinesValue.get(Calendar.YEAR), deadlinesValue
                        .get(Calendar.MONTH), deadlinesValue
                        .get(Calendar.DAY_OF_MONTH));

                dpd.show();
                break;
            case R.id.btnEmail:
                showInputFieldDialog(Constants.EMAIL);
                break;

            case R.id.btnPhone:
                showInputFieldDialog(Constants.PHONE);
                break;
            case R.id.btnUrl:
                showInputFieldDialog(Constants.URL);
                break;
            case R.id.btnSubmit:
                if(getInputData(true)){
                    saveOrUpdateToDoTask(toDoModel,isUpdate);
                }
                break;
            default:
                break;

        }
    }

    private boolean  getInputData(boolean isMandetoryChack){
        if((etTaskName.getText()==null || etTaskName.getText().toString().trim().equals("")) && isMandetoryChack){
            Toast.makeText(this,"Please enter task name.",Toast.LENGTH_SHORT).show();
            etTaskName.requestFocus();
            return false;
        }else{
            toDoModel.setTaskName(etTaskName.getText()+"");
        }

        if((etDescription.getText()==null || etDescription.getText().toString().trim().equals("")) && isMandetoryChack){
            Toast.makeText(this,"Please enter description.",Toast.LENGTH_SHORT).show();
            etDescription.requestFocus();
            return false;

        }else{
            toDoModel.setDescription(etDescription.getText().toString());
        }

        if((tvDeadLine.getText()==null || tvDeadLine.getText().toString().trim().equals("")) && isMandetoryChack){
            Toast.makeText(this,"Please select deadlines.",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            toDoModel.setDeadline(tvDeadLine.getText()+"");
        }

        toDoModel.setStatus(spStatus.getSelectedItem().toString());
        return true;
    }

    void saveOrUpdateToDoTask(ToDoModel toDoModel, boolean isUpdate){
        db = new DatabaseHandler(this);
        db.openDatabase();

        if(isUpdate){
            if(db.updateTask(toDoModel)>0){
                showAlertDialog(this.getString(R.string.msg_update));
            }

        }else {
            if(db.insertTask(toDoModel)>0){
                showAlertDialog(this.getString(R.string.msg_success));
            }
        }


    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId()==R.id.spStatus){
            toDoModel.setStatus(parent.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void showAlertDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskManageActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_view, viewGroup, false);
        TextView tv=(TextView)dialogView.findViewById(R.id.tvMsg);
        tv.setText(msg);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button btnOk=(Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent=new Intent(TaskManageActivity.this,HomeActivity.class);
                startActivity(intent);

            }
        });
    }

    void showInputFieldDialog(String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskManageActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_input_dialog, viewGroup, false);
        ImageView ivType=(ImageView) dialogView.findViewById(R.id.ivType);
        EditText etFieldType=(EditText) dialogView.findViewById(R.id.etFieldType);
        Button btnSave=(Button) dialogView.findViewById(R.id.btnSave);
        if(type.equals(Constants.EMAIL)){
//            Glide.with(this)
//                    .load(this.getResources().getIdentifier("ic_email", "drawable", this.getPackageName()))
//                    .into(ivType);
            ivType.setBackground(getDrawable(R.drawable.ic_email));
            etFieldType.setHint(getString(R.string.enter_email));
            btnSave.setText(getString(R.string.save_email));
            if(toDoModel.getEmail()!=null && !toDoModel.getEmail().equals("")){
                etFieldType.setText(toDoModel.getEmail());
            }

        }
        else if(type.equals(Constants.PHONE)){
//            Glide.with(this)
//                    .load(this.getResources().getIdentifier("ic_phone", "drawable", this.getPackageName()))
//                    .into(ivType);
            ivType.setBackground(getDrawable(R.drawable.ic_phone));
            etFieldType.setHint(getString(R.string.enter_phone));
            btnSave.setText(getString(R.string.save_phone));
            if(toDoModel.getPhoneNo()!=null && !toDoModel.getPhoneNo().equals("")){
                etFieldType.setText(toDoModel.getPhoneNo());
            }
        }
        else if(type.equals(Constants.URL)){
//            Glide.with(this)
//                    .load(this.getResources().getIdentifier("ic_url", "drawable", this.getPackageName()))
//                    .into(ivType);
            ivType.setBackground(getDrawable(R.drawable.ic_url));
            etFieldType.setHint(getString(R.string.enter_url));
            btnSave.setText(getString(R.string.save_url));
            if(toDoModel.getUrl()!=null && !toDoModel.getUrl().equals("")){
                etFieldType.setText(toDoModel.getUrl());
            }
        }



        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etFieldType.getText()!=null && !etFieldType.getText().toString().equals("")){
                    if(type.equals(Constants.EMAIL)){
                        toDoModel.setEmail(etFieldType.getText().toString());
                    }
                    else if(type.equals(Constants.PHONE)){
                        toDoModel.setPhoneNo(etFieldType.getText().toString());
                    }
                    else if(type.equals(Constants.URL)){
                        toDoModel.setUrl(etFieldType.getText().toString());
                    }

                }


                alertDialog.dismiss();


            }
        });
    }

}