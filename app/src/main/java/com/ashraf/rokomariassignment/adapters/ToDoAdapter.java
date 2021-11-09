package com.ashraf.rokomariassignment.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.ashraf.rokomariassignment.HomeActivity;
import com.ashraf.rokomariassignment.R;
import com.ashraf.rokomariassignment.TaskDetailsActivity;
import com.ashraf.rokomariassignment.TaskManageActivity;
import com.ashraf.rokomariassignment.model.ToDoModel;
import com.ashraf.rokomariassignment.utils.DatabaseHandler;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.Viewholder> {

    private Context context;
    private ArrayList<ToDoModel> toDoModelArrayList=null;
    private DatabaseHandler db;
    String status="";


    public ToDoAdapter(Context context, ArrayList<ToDoModel> toDoModelArrayList,String status) {
        this.context = context;
        this.toDoModelArrayList = toDoModelArrayList;
        this.status=status;
        db = new DatabaseHandler(context);
        db.openDatabase();

    }

    @NonNull
    @Override
    public ToDoAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        ToDoModel toDoModel = toDoModelArrayList.get(position);
        holder.tvTaskName.setText(": "+toDoModel.getTaskName());
        holder.tvCreatedDate.setText(": "+ toDoModel.getCreatedOn());
        holder.tvDeadLine.setText(": "+toDoModel.getDeadline());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskDetailsActivity.class);
                intent.putExtra("todoTask", toDoModel);
                context.startActivity(intent);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskManageActivity.class);
                intent.putExtra("isUpdate", true);
                intent.putExtra("todoTask", toDoModel);
                context.startActivity(intent);

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.deleteTask(toDoModel.getId())>0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_view, null, false);
                    TextView tv=(TextView)dialogView.findViewById(R.id.tvMsg);
                    tv.setText(context.getString(R.string.msg_delete));
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Button btnOk=(Button) dialogView.findViewById(R.id.btnOk);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            Intent intent = new Intent(context, HomeActivity.class);
                            context.startActivity(intent);

                        }
                    });

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return toDoModelArrayList.size();
    }





    public class Viewholder extends RecyclerView.ViewHolder {
        public ImageButton btnEdit, btnDelete;
        public TextView tvTaskName, tvCreatedDate,tvDeadLine;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
            tvDeadLine = itemView.findViewById(R.id.tvDeadLine);

            btnEdit=(ImageButton) itemView.findViewById(R.id.btnEdit);
            btnDelete=(ImageButton) itemView.findViewById(R.id.btnDelete);


        }
    }
}

