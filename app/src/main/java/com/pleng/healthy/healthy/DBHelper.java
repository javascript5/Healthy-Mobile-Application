package com.pleng.healthy.healthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.pleng.healthy.healthy.AlarmClock.SleepStore;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    public DBHelper(Context context) {
        super(context, "devahoy_friends.db", null, 1);
        this.context = context;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FRIEND_TABLE = "CREATE TABLE time(date VARCHAR(200), time1 VARCHAR(200), time2 VARCHAR(200), differenceTime VARCHAR(200))";
        db.execSQL(CREATE_FRIEND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS time";

        db.execSQL(DROP_FRIEND_TABLE);

        onCreate(db);
    }


    public void addTime(SleepStore sleepStore) {
        try{
            sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("date", sleepStore.getDate());
            values.put("time1", sleepStore.getSleepTime());
            values.put("time2", sleepStore.getWakeUpTime());
            values.put("differenceTime", sleepStore.getDifferenceTime());
            sqLiteDatabase.insert("time", null, values);
            Toast.makeText(context,"บันทึกข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.close();
        }catch (SQLiteConstraintException e){
            Log.i("Time", e.getMessage());
            Toast.makeText(context,"บันทึกข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
        }
    }
    private  SleepStore sleepStore;
    public List<SleepStore> getTime() {
        List<SleepStore> sleepArray = new ArrayList<SleepStore>();

        try{
            sqLiteDatabase = this.getWritableDatabase();

            Cursor cursor = sqLiteDatabase.query
                    ("time", null, null, null, null, null, "date DESC");

            if (cursor != null) {
                cursor.moveToFirst();
            }

            while(!cursor.isAfterLast()) {
                sleepStore = new SleepStore(cursor.getString(0), cursor.getString(1), cursor.getString(2) ,cursor.getString(3));

                sleepArray.add(sleepStore);
                cursor.moveToNext();
            }

            sqLiteDatabase.close();
        }catch (SQLiteConstraintException e){
            Log.i("Time", e.getMessage());
            Toast.makeText(context,"โหลดข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
        }

        return sleepArray;
    }
}
