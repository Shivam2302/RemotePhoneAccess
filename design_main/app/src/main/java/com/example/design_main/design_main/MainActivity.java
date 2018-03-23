package com.example.design_main.design_main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.design_main.design_main.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent in = new Intent(this, MyService.class);
        startService(in);

    }

//     this method is automatically called as soon as the app is stared......
    @Override
    public void onStart(){

        super.onStart();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView;
        final EditText password,confirmPassword;
        Button submit,cancel;

        // if app is started first time user is given a chance to set up his/her password....
        if(checkAppInstalation()){

            mView = getLayoutInflater().inflate(R.layout.installation_password_alert,null);
            password = (EditText) mView.findViewById(R.id.etPassword);
            confirmPassword = (EditText) mView.findViewById(R.id.etConfirmPassword);
            submit = (Button) mView.findViewById(R.id.submit);

            // on submit, equality of password and confirm password is checked and if they are equal password is stored.....
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!password.getText().toString().isEmpty()&&!confirmPassword.getText().toString().isEmpty()){

                        if(password.getText().toString().equals(confirmPassword.getText().toString())){

                            setPassword(password.getText().toString());
                            Intent i = new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(i);

                        }
                        else{

                            Toast.makeText(MainActivity.this, "password and confirm password don't match.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        // if app is not started first time, password entered by the user is checked.
        else{

            mView = getLayoutInflater().inflate(R.layout.password_alert,null);
            password = (EditText) mView.findViewById(R.id.etPassword);
            submit = (Button) mView.findViewById(R.id.submit);

            // on submit, equality of entered value is checked with password in database and if they are equal user is directed to main page.....
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!password.getText().toString().isEmpty()){

                        SharedPreferences mshared = getSharedPreferences("Main",MODE_PRIVATE);

                        if(mshared.getString("primaryPassword","").equals(password.getText().toString())){

                            Intent i = new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please fill your password.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        // clicking cancel will close the app
        cancel = (Button) mView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    // when app is opened first time, this method is called to set initial value of commands....
    private void setInitialCommands(){
        SharedPreferences mshared = getSharedPreferences("Main", MODE_PRIVATE);
        SharedPreferences.Editor editor = mshared.edit();
        editor.putString("access","DisableAccess");
        editor.putString("contact","GetContact");
        editor.putString("notification","Notify");
        editor.putString("mode","ChangeMode");
        editor.putString("location","GetLocation");
        editor.putString("lock","Lock");
        editor.putString("format","Format");
        editor.apply();

    }


    //this method is used to check whether app is opened first time or not.
    private boolean checkAppInstalation(){

        SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);
        SharedPreferences.Editor editor = mShared.edit();
        if(mShared.getString("primaryPassword","").equals("")){
            return true;
        }
        else{
            return false;
        }
    }


    // this method is used to set password.
    private void setPassword(String password) {

        SharedPreferences mshared = getSharedPreferences("Main", MODE_PRIVATE);
        SharedPreferences.Editor editor = mshared.edit();
        editor.putString("primaryPassword",password);
        editor.putString("secondaryPassword",password);
        editor.putString("email","Not Set");
        editor.apply();
        setInitialCommands();
    }

}