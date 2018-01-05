package com.adamnain.android.dailycost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.value;
import static android.R.attr.x;
import static android.widget.Toast.makeText;
import static com.adamnain.android.dailycost.R.id.activity_main;
//import static com.adamnain.android.dailycost.R.id.displayUser;
import static com.adamnain.android.dailycost.R.id.password;
import static com.adamnain.android.dailycost.R.id.username;

/**
 * Created by adamnain on 19/04/2017.
 */

public class SignIn  extends AppCompatActivity {
    DatabaseHelperLog helper = new DatabaseHelperLog(this);

    //String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);


    }

    public void onSignInClick(View v){
        if(v.getId() == R.id.signInClick){
            EditText a = (EditText)findViewById(username);
            String str = a.getText().toString();
            EditText b = (EditText)findViewById(password);
            String pass = b.getText().toString();

            String password = helper.searchPass(str);
            if(pass.equals(password)){
                Intent i = new Intent(SignIn.this, MainActivity.class);

                //code 19 mei
                i.putExtra("uname", str);
                startActivity(i);

            }
            else{
                Toast tmp = Toast.makeText(SignIn.this, "Username and Password don't match",Toast.LENGTH_SHORT);
                tmp.show();
            }



        }
    }


    public void onSignUpClick(View v) {
        if (v.getId() == R.id.signUpClick) {
            Intent i = new Intent(SignIn.this, SignUp.class);
            startActivity(i);
        }
        /*boolean kondisi = helper.checkDatabase();
        if(kondisi){
            Toast tmp = Toast.makeText(SignIn.this, "You are registered!",Toast.LENGTH_SHORT);
            tmp.show();
        }
        else {
            Intent i = new Intent(SignIn.this, SignUp.class);
            startActivity(i);
        }
        */
    }

}
