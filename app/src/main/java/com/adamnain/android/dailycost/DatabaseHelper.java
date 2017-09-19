package com.adamnain.android.dailycost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
//import static com.adamnain.android.dailycost.R.id.uname;
import static com.adamnain.android.dailycost.R.id.username;

/**
 * Created by adamnain on 30/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String nama_db = "dailycost.db";
    public static final String nama_tabel = "catatan";
    public static final String kolom_1 = "id";
    public static final String kolom_2 = "tanggal";
    public static final String kolom_3 = "note";
    public static final String kolom_4 = "income";
    public static final String kolom_5 = "outcome";
    public static final String kolom_6 = "username";

    public DecimalFormat dec = new DecimalFormat("0,000.00");

    public DatabaseHelper(Context context){
        super(context, nama_db, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+nama_tabel+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TANGGAL DATETIME, NOTE TEXT, INCOME INTEGER, OUTCOME INTEGER, USERNAME TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+nama_tabel);
        onCreate(db);
    }

    public final int kosong = 0;

    public boolean insertDataIncome(String tanggal, String note, String income, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(kolom_2, tanggal);
        contentValues.put(kolom_3, note);
        contentValues.put(kolom_4, income);
        contentValues.put(kolom_5, kosong);
        contentValues.put(kolom_6, username);
        long result = db.insert(nama_tabel, null, contentValues);
        //if (result == -1){
        if (tanggal.equals("") || note.equals("") || income.equals("")){
            return false;
        }
        else
            return true;
    }



    public boolean insertDataOutcome(String tanggal, String note, String outcome, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(kolom_2, tanggal);
        contentValues.put(kolom_3, note);
        contentValues.put(kolom_4, kosong);
        contentValues.put(kolom_5, outcome);
        contentValues.put(kolom_6, username);
        long result = db.insert(nama_tabel, null, contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }



    //tampil data di listview dan toast
    public Cursor getAllData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+nama_tabel+" where username='"+username+"'",null);
        return res;
    }

    //hapus data by id
    public void deleteRow(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+nama_tabel+" where id="+id);
        db.close();
    }



    //tampilkan data di jumlah
    public String getSaldo(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT SUM(income-outcome) from catatan where username ='"+username+"'", null);

        int tau = 0;
        if (cur.moveToFirst()) {
            tau = cur.getInt(0);
        }
        String mySaldo = String.valueOf(dec.format(tau));
        String pesan = ("Rp."+mySaldo);
        return pesan;
    }

}
