package com.adamnain.android.dailycost;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
//import android.icu.text.DateFormat;
import android.text.format.DateFormat;
import java.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import static android.R.attr.name;
import static android.R.attr.onClick;
import static android.R.attr.x;
import static android.R.id.edit;
import static android.R.id.message;
import static com.adamnain.android.dailycost.R.id.editText_tanggal;
import static com.adamnain.android.dailycost.R.id.getUname;
import static com.adamnain.android.dailycost.R.id.ib_help;
import static com.adamnain.android.dailycost.R.id.saldoini;
//import static com.adamnain.android.dailycost.R.id.uname;


public class MainActivity extends AppCompatActivity implements
DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    DatabaseHelper myDb;
    EditText editTanggal, editNote, editMoney;
    Button btnIncome, btnOutcome, btnView;
    TextView saldo, urname;
    ImageButton ib_help;

    //datetimepicker inisialisasi
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editTanggal = (EditText) findViewById(editText_tanggal);
        editNote = (EditText) findViewById(R.id.editText_note);
        editMoney = (EditText) findViewById(R.id.editText_money);
        btnIncome = (Button) findViewById(R.id.button_income);
        btnOutcome = (Button) findViewById(R.id.button_outcome);
        btnView = (Button) findViewById(R.id.button_view);
        saldo = (TextView)findViewById(saldoini);
        urname = (TextView)findViewById(getUname);
        ib_help = (ImageButton)findViewById(R.id.ib_help);

        //set uname
        Bundle extras = getIntent().getExtras();
        String uname = null;
        if(extras != null)
            uname = extras.getString("uname");
        urname.setText(uname);

        //set dynamic text untuk saldo
        String mySaldo = myDb.getSaldo(uname);
        saldo.setText(mySaldo);

        addDataIncome();
        addDataOutcome();
        //with toast
        viewAll();
        showNotification();

        //datetime picker
        editTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this, year,month,day);
                datePickerDialog.show();
            }
        });

        onQuestionClick();

    }



    public void addDataIncome(){
        btnIncome.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertDataIncome(editTanggal.getText().toString(),
                                editNote.getText().toString(),
                                editMoney.getText().toString(),urname.getText().toString());

                        TextView saldo = (TextView)findViewById(saldoini);
                        String mySaldo = myDb.getSaldo(urname.getText().toString());
                        saldo.setText(mySaldo);

                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Data Inserted "+" ",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted",Toast.LENGTH_LONG).show();

                    }
                }
        );

    }

    public void addDataOutcome(){
        btnOutcome.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.insertDataOutcome(editTanggal.getText().toString(),
                                editNote.getText().toString(),
                                editMoney.getText().toString(),urname.getText().toString());

                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted",Toast.LENGTH_LONG).show();

                        TextView saldo = (TextView)findViewById(saldoini);
                        String mySaldo = myDb.getSaldo(urname.getText().toString());
                        saldo.setText(mySaldo);
                    }
                }
        );
    }


    public void viewAll(){
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MainActivity.this, ViewAll.class);
                        intent.putExtra("uname",urname.getText().toString());
                        startActivity(intent);


                        /*
                        Cursor res = myDb.getAllData(urname.getText().toString());
                        if (res.getCount() == 0){
                            showMessage("no data","tidak ada");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            //buffer.append("Id :"+res.getString(0)+"\n");
                            buffer.append("- Date :"+res.getString(1)+"\n");
                            buffer.append("Note :"+res.getString(2)+"\n");
                            buffer.append("Income :"+res.getString(3)+"\n");
                            buffer.append("Outcome :"+res.getString(4)+"\n\n");
                        }

                        //show message
                        showMessage("Data",buffer.toString());
                        */
                    }
                }

        );
    }


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    //code 12 April 2017
    public void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logoproject);
        builder.setContentTitle("DailyCost");
        String mySaldo = myDb.getSaldo(urname.getText().toString());
        builder.setContentText(urname.getText().toString()+"'s money "+mySaldo);
        Intent intent = new Intent (this,SignIn.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0,builder.build());
    }

    //datetimepicker
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1+1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, MainActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        editTanggal.setText(yearFinal+"-"+monthFinal+"-"+dayFinal+" "+hourFinal+":"+minuteFinal);
    }

    public void onQuestionClick(){
        ib_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message =
                        "1. Type amount of your income or outcome money field"+"\n"+
                        "2. Type a note about your money in note field"+"\n"+
                        "3. Double click on date field to set time "+"\n"+
                        "4. click on income button its an income or outcome button if its an outcome"+"\n"+
                        "5. click on view all data button to see data details"+"\n"+
                        "6. tap the data to delete"+"\n\n"+
                        "Enjoy it thanks for using this app :)"+"\n"+
                        "This app developed by:"+"\n"+
                        "Muhammad Adam Dzulqarnain"+"\n"+
                        "1157050100";
                showMessage("How to use this app?",message);
            }
        });

    }

}//akhir program
