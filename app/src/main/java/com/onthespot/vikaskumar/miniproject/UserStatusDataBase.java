package com.onthespot.vikaskumar.miniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */

public class UserStatusDataBase {

    static UserStatusDataBase statusDataBase;
    UserStatusHelper helper;
    private SQLiteDatabase mDatabase;
    private Context context;

    public UserStatusDataBase(Context context) {
        helper = new UserStatusHelper(context);
        this.context = context;
    }

    public static UserStatusDataBase newInstance(Context context) {
        if (statusDataBase == null) {
            statusDataBase = new UserStatusDataBase(context);
        }
        return statusDataBase;
    }


    public void insertRow(String name,String status,String token,String email){

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(helper.Name, name);
        contentValues.put(helper.userStatus, status);
        contentValues.put(helper.timeStamp,token);
        contentValues.put(helper.UserEmail,email);
        db.insert(helper.Tablename, null, contentValues);

    }


    public ArrayList<UserStausModel> viewAllData() {

        ArrayList<UserStausModel> list = new ArrayList<>();
        String[] columns = {helper.UID, helper.Name, helper.userStatus,helper.timeStamp,helper.UserEmail};
        StringBuffer buffer = new StringBuffer();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(helper.Tablename, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            UserStausModel staus = new UserStausModel();
            int index1 = cursor.getColumnIndex(helper.UID);
            int id = cursor.getInt(index1);
            UserStausModel.setUserName(cursor.getString(cursor.getColumnIndex(helper.Name)));
            UserStausModel.setStatus(cursor.getString(cursor.getColumnIndex(helper.userStatus)));
            UserStausModel.setEmail(cursor.getString(cursor.getColumnIndex(helper.UserEmail)));
            UserStausModel.setToken(cursor.getString(cursor.getColumnIndex(helper.timeStamp)));
            list.add(staus);
        }

        return list;
    }


    public void deleteDatabase(){
        //mDatabase.delete(UserDataBaseHelper.Tablename,null,null);
        context.deleteDatabase(helper.DataBaseName);

    }

    public class UserStatusHelper extends SQLiteOpenHelper {

        private static final int DataBaseVersion = 1;
        private static final String DataBaseName = "userStatusDatabase";
        private static final String Tablename = "userTable";
        private static final String UID = "id";
        private static final String Name = "name";
        private static final String userStatus = "userStatus";
        private static final String timeStamp = "time";
        private static final String UserEmail = "email";
        private static final String CREATETABLE = "CREATE TABLE " +
                Tablename + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Name + " VARCHAR(250), " + timeStamp + " VARCHAR(250), "+ UserEmail +
                " VARCHAR(250), " + userStatus + " VARCHAR(250));";

        private static final String DROPTABLE = "DROP TABLE IF EXISTS" + Tablename;

        public UserStatusHelper(Context context) {
            super(context, DataBaseName, null, DataBaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATETABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROPTABLE);
            onCreate(db);
        }
    }




}
