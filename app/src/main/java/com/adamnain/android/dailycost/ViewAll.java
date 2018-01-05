package com.adamnain.android.dailycost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adamnain.android.dailycost.DetailListAdapter;
import com.adamnain.android.dailycost.R;
import com.adamnain.android.dailycost.detail;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by adamnain on 30/05/2017.
 */

public class ViewAll extends Activity {
    private ListView lvDetail;
    private DetailListAdapter adapter;
    private List<detail> mDetailList;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);

        final TextView lvUsername = (TextView)findViewById(R.id.tv_vusername);
        lvDetail = (ListView)findViewById(R.id.listview_detail);
        ImageButton lvBack = (ImageButton)findViewById(R.id.back);

        mDetailList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        String uname = null;
        if(extras != null)
            uname = extras.getString("uname");

        lvUsername.setText(uname);
        Cursor cursor = myDb.getAllData(uname);


        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(0);
                    String date = cursor.getString(1);
                    String income = "INCOME : Rp."+ cursor.getString(3);
                    String outcome = "OUTCOME: Rp."+ cursor.getString(4);
                    String note = cursor.getString(2);
                    mDetailList.add(new detail(id,date,income,outcome,note));
                }while (cursor.moveToNext());
            }
        }
        else{
            Intent intent = new Intent(ViewAll.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(ViewAll.this, "Data is Empty",Toast.LENGTH_SHORT).show();
        }

        /*
        mDetailList.add(new detail(1, "1998-01-01", "2000","jajan"));
        mDetailList.add(new detail(2, "1999-01-01", "2000","jajan"));
        mDetailList.add(new detail(3, "2000-01-01", "2000","jajan"));
        */


        //init adapter

        adapter = new DetailListAdapter(getApplicationContext(), mDetailList);
        lvDetail.setAdapter(adapter);

        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int id = (int) view.getTag();

                //alert dialog
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(ViewAll.this);
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //delete method
                                myDb.deleteRow(id);
                                Intent ii = new Intent(ViewAll.this, ViewAll.class);
                                ii.putExtra("uname",lvUsername.getText().toString());
                                startActivity(ii);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //nothing
                                dialogInterface.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_delete)
                        .show();


                //Toast.makeText(getApplicationContext(), "clicked product id="+view.getTag(), Toast.LENGTH_SHORT).show();
            }

        });


        //back
        lvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAll.this, MainActivity.class);
                intent.putExtra("uname",lvUsername.getText().toString());
                startActivity(intent);
            }
        });
    }
}
