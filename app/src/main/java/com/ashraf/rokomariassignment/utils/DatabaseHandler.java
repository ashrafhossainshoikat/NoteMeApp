package com.ashraf.rokomariassignment.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.ashraf.rokomariassignment.model.ToDoModel;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "note_me_db";
    private static final String NOTE_ME_TASK_TABLE = "note_me_task";
    private static final String ID = "id";
    private static final String TASK_NAME = "task_name";
    private static final String DESCRIPTION = "description";
    private static final String DEADLINES = "deadlines";
    private static final String STATUS = "status";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String URL = "url";
    private static final String CREATED_ON = "created_on";
    private static final String UPDATED_ON = "updated_on";



    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + NOTE_ME_TASK_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_NAME + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + DEADLINES + " TEXT, "
            + STATUS + " TEXT, "
            + EMAIL + " TEXT, "
            + PHONE + " TEXT, "
            + URL + " TEXT, "
            + CREATED_ON + " TEXT, "
            + UPDATED_ON + " TEXT)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_ME_TASK_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public long insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK_NAME, task.getTaskName());
        cv.put(DESCRIPTION, task.getDescription());
        cv.put(DEADLINES, task.getDeadline());
        cv.put(STATUS, task.getStatus());
        cv.put(EMAIL, task.getEmail());
        cv.put(PHONE, task.getPhoneNo());
        cv.put(URL, task.getUrl());
        cv.put(CREATED_ON, Utility.getDateFromMillisecond(Calendar.getInstance().getTimeInMillis(),Constants.ddIMMIyyyyHHmmss));
        return db.insert(NOTE_ME_TASK_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public ArrayList<ToDoModel> getAllTasks(){
        ArrayList<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(NOTE_ME_TASK_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTaskName(cur.getString(cur.getColumnIndex(TASK_NAME)));
                        task.setDescription(cur.getString(cur.getColumnIndex(DESCRIPTION)));
                        task.setDeadline(cur.getString(cur.getColumnIndex(DEADLINES)));
                        task.setStatus(cur.getString(cur.getColumnIndex(STATUS)));
                        task.setEmail(cur.getString(cur.getColumnIndex(EMAIL)));
                        task.setPhoneNo(cur.getString(cur.getColumnIndex(PHONE)));
                        task.setUrl(cur.getString(cur.getColumnIndex(URL)));
                        task.setCreatedOn(cur.getString(cur.getColumnIndex(CREATED_ON)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    @SuppressLint("Range")
    public ArrayList<ToDoModel> getAllTasksByStatus(String status){
        ArrayList<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            String sql=" SELECT * FROM note_me_task WHERE status= '"+status+"' ";
            cur = db.rawQuery(sql,null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTaskName(cur.getString(cur.getColumnIndex(TASK_NAME)));
                        task.setDescription(cur.getString(cur.getColumnIndex(DESCRIPTION)));
                        task.setDeadline(cur.getString(cur.getColumnIndex(DEADLINES)));
                        task.setStatus(cur.getString(cur.getColumnIndex(STATUS)));
                        task.setEmail(cur.getString(cur.getColumnIndex(EMAIL)));
                        task.setPhoneNo(cur.getString(cur.getColumnIndex(PHONE)));
                        task.setUrl(cur.getString(cur.getColumnIndex(URL)));
                        task.setCreatedOn(cur.getString(cur.getColumnIndex(CREATED_ON)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }



    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(NOTE_ME_TASK_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public long updateTask(ToDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK_NAME, task.getTaskName());
        cv.put(DESCRIPTION, task.getDescription());
        cv.put(DEADLINES, task.getDeadline());
        cv.put(STATUS, task.getStatus());
        cv.put(EMAIL, task.getEmail());
        cv.put(PHONE, task.getPhoneNo());
        cv.put(URL, task.getUrl());
        cv.put(UPDATED_ON, Utility.getDateFromMillisecond(Calendar.getInstance().getTimeInMillis(),Constants.ddIMMIyyyyHHmmss));
        return db.update(NOTE_ME_TASK_TABLE, cv, ID + "= ?", new String[] {String.valueOf(task.getId())});
    }

    public long deleteTask(int id){
       return db.delete(NOTE_ME_TASK_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}
