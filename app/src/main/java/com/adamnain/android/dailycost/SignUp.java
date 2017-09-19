package com.adamnain.android.dailycost;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by adamnain on 19/04/2017.
 */

public class SignUp extends Activity {

    DatabaseHelperLog helper = new DatabaseHelperLog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void onSignUpClick2(View v){
        if(v.getId() == R.id.button_signup){
            EditText username = (EditText)findViewById(R.id.username);
            EditText password = (EditText)findViewById(R.id.password);
            EditText password2 = (EditText)findViewById(R.id.conpass);

            String unamestr = username.getText().toString();
            String pass1str = password.getText().toString();
            String pass2str = password2.getText().toString();

            if(!pass1str.equals(pass2str)){
                Toast pass = Toast.makeText(SignUp.this, "Password don't match!", Toast.LENGTH_SHORT);
                pass.show();
            }
            else{
                boolean cek = helper.checkDatabase(unamestr);

                if (cek){
                    Toast pass = Toast.makeText(SignUp.this, "username is exist", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else{
                    UserList u = new UserList();
                    u.setUsername(unamestr);
                    u.setPassword(pass1str);

                    helper.insertUserList(u);
                    Toast pass = Toast.makeText(SignUp.this, "You are registered thankyou", Toast.LENGTH_SHORT);
                    pass.show();
                }

            }
        }
    }
}
