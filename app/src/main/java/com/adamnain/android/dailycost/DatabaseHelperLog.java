package com.adamnain.android.dailycost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adamnain on 19/04/2017.
 */

public class DatabaseHelperLog extends SQLiteOpenHelper {



    //login dan sign up code on 19 April
    private static final int database_version = 1;
    private static  final String nama_db_login = "userlist.db";
    private static  final String nama_tabel_login = "userlist";
    private static  final String kolom_id = "id";
    private static  final String kolom_username = "username";
    private static  final String kolom_password = "password";

    SQLiteDatabase dbl;
    private static final String create_login_table = "create table userlist(id int primary key not null, username text not null, password text not null)";

    public DatabaseHelperLog(Context context){
        super(context, nama_db_login, null, database_version);
    }
    @Override
    public void onCreate(SQLiteDatabase dbl){
        dbl.execSQL(create_login_table);
        this.dbl = dbl;
    }
    @Override
    public void onUpgrade(SQLiteDatabase dbl, int oldVersion, int newVersion){
        String query = "drop table if exists"+nama_tabel_login;
        dbl.execSQL(query);
        this.onCreate(dbl);
    }

    public String searchPass(String username){
        dbl = this.getReadableDatabase();
        String query = "select username, password from "+nama_tabel_login;
        Cursor cursor = dbl.rawQuery(query , null);
        String a,b;
        b = "not found";
        if (cursor.moveToFirst()){
            do{
                a = cursor.getString(0);
                b = cursor.getString(1);

                if(a.equals(username)){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }

    public void insertUserList(UserList u){
        dbl = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from userlist";
        Cursor cursor = dbl.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(kolom_id, count );
        values.put(kolom_username, u.getUsername() );
        values.put(kolom_password, u.getPassword() );

        dbl.insert(nama_tabel_login, null, values);
        dbl.close();
    }


    //cek username database
    public boolean checkDatabase(String username){
        dbl = this.getWritableDatabase();
        String query = "select * from userlist where username='"+username+"'";
        Cursor cursor = dbl.rawQuery(query, null);
        boolean exist;
        if(cursor.moveToFirst()){
            exist = true;
        }
        else {
            exist = false;
        }
        return exist;
    }


}
