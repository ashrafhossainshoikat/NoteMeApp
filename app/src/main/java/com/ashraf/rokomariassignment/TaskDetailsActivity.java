package com.ashraf.rokomariassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ashraf.rokomariassignment.model.ToDoModel;
import com.ashraf.rokomariassignment.utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvCreatedDate,tvTaskName,tvStatus,tvDescription,tvDeadlines;
    FloatingActionButton fab;
    ImageButton btnDelete;

    ToDoModel toDoModel=new ToDoModel();
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        getSupportActionBar().hide();

        tvCreatedDate=(TextView) findViewById(R.id.tvCreatedDate);
        tvTaskName=(TextView) findViewById(R.id.tvTaskName);
        tvStatus=(TextView) findViewById(R.id.tvStatus);
        tvDescription=(TextView) findViewById(R.id.tvDescription);
        tvDeadlines=(TextView) findViewById(R.id.tvDeadlines);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        btnDelete=(ImageButton)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        if (b!=null && b.containsKey("todoTask")) {
            toDoModel=(ToDoModel) this.getIntent().getSerializableExtra("todoTask");
            tvCreatedDate.setText(toDoModel.getCreatedOn());
            tvStatus.setText(toDoModel.getStatus());
            tvDescription.setText(toDoModel.getDescription());
            tvDeadlines.setText(toDoModel.getDeadline());
            tvTaskName.setText(toDoModel.getTaskName());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete:
                db = new DatabaseHandler(this);
                db.openDatabase();
                if(db.deleteTask(toDoModel.getId())>0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_view, null, false);
                    TextView tv=(TextView)dialogView.findViewById(R.id.tvMsg);
                    tv.setText(this.getString(R.string.msg_delete));
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Button btnOk=(Button) dialogView.findViewById(R.id.btnOk);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            Intent intent = new Intent(TaskDetailsActivity.this, HomeActivity.class);
                            startActivity(intent);

                        }
                    });

                }

                break;

            case R.id.fab:
                Intent intent = new Intent(TaskDetailsActivity.this, TaskManageActivity.class);
                intent.putExtra("isUpdate", true);
                intent.putExtra("todoTask", toDoModel);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}