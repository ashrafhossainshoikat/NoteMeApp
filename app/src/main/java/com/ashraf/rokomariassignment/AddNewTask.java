package com.ashraf.rokomariassignment;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ashraf.rokomariassignment.model.ToDoModel;
import com.ashraf.rokomariassignment.utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Objects;

public class AddNewTask extends Fragment {

//    public static final String TAG = "ActionBottomDialog";
//    private EditText etTaskName, etDescription;
//    private TextView tvDeadLine;
//    private Spinner spStatus;
//    private Button newTaskSaveButton;
//
//    private DatabaseHandler db;
//
//    public static AddNewTask newInstance(){
//        return new AddNewTask();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.new_task, container, false);
//        etTaskName = (EditText) rootView.findViewById(R.id.etTaskName);
//        return rootView;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        etTaskName = requireView().findViewById(R.id.etTaskName);
//        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);
//
//        boolean isUpdate = false;
//
//        final Bundle bundle = getArguments();
//        if(bundle != null){
//            isUpdate = true;
//            String task = bundle.getString("task");
//            etTaskName.setText(task);
//            assert task != null;
//            if(task.length()>0)
//                newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
//        }
//
//        db = new DatabaseHandler(getActivity());
//        db.openDatabase();
//
//        etTaskName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString().equals("")){
//                    newTaskSaveButton.setEnabled(false);
//                    newTaskSaveButton.setTextColor(Color.GRAY);
//                }
//                else{
//                    newTaskSaveButton.setEnabled(true);
//                    newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//        final boolean finalIsUpdate = isUpdate;
//        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = etTaskName.getText().toString();
//                if(finalIsUpdate){
//                    db.updateTask(bundle.getInt("id"), text);
//                }
//                else {
//                    ToDoModel task = new ToDoModel();
//                    task.setTaskName(text);
//                    task.setStatus("Pending");
//                    db.insertTask(task);
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void onDismiss(@NonNull DialogInterface dialog){
//        Activity activity = getActivity();
//        if(activity instanceof DialogCloseListener)
//            ((DialogCloseListener)activity).handleDialogClose(dialog);
//    }
}
