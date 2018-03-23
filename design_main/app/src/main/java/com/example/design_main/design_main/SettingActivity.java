package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.design_main.design_main.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LinearLayout primaryEmail = (LinearLayout) findViewById(R.id.email);
        LinearLayout primary = (LinearLayout) findViewById(R.id.primary_password);
        LinearLayout secondary = (LinearLayout) findViewById(R.id.secondary_password);

        SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);

        // fetching values from the database and showing it on the setting page.....
        TextView email = (TextView) findViewById(R.id.email_value);
        email.setText(mShared.getString("email","-1"));

        TextView primaryPassword = (TextView) findViewById(R.id.primary_password_value);
        primaryPassword.setText(mShared.getString("primaryPassword","-1"));

        TextView secondaryPassword = (TextView) findViewById(R.id.secondary_password_value);
        secondaryPassword.setText(mShared.getString("secondaryPassword","-1"));


        // on clicking email field an alert box is opened that gives us a chance to change primary email address.....
        primaryEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);

                String heading1 = "Change Primary Email Address";
                String heading2 = "New Primary Email Address";
                String purpose = "All the information about the number which is accessing your phone will be sent to this email.";
                String current = mShared.getString("email","-1");
                String key = "email";

                openAlertBox(heading1,purpose,key,current,heading2);

            }
        });


        // on clicking primary password field an alert box is opened that gives us a chance to change the primary password.....
        primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);

                String heading1 = "Change Primary Password";
                String heading2 = "New Primary Password";
                String purpose = "This password is used to open your app. Also in order to get information from the phone " +
                        "you have to send this password along with the message.";
                String current = mShared.getString("primaryPassword","-1");
                String key = "primaryPassword";

                openAlertBox(heading1,purpose,key,current,heading2);
            }
        });


        // on clicking secondary password field an alert box is opened that gives us a chance to change the secondary password.....
        secondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);

                String heading1 = "Change Secondary Password";
                String heading2 = "New Secondary Password";
                String purpose = "This password is used for disabling access from some other phone in case of misuse of this app.";
                String current = mShared.getString("secondaryPassword","-1");
                String key = "secondaryPassword";

                openAlertBox(heading1,purpose,key,current,heading2);

            }
        });


        // on clicking ? a alert box is opened that gives us information about the field.....
        ImageView help = (ImageView) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.help,null);
                mBuilder.setView(mView);

                final AlertDialog dialog = mBuilder.create();

                Button ok = (Button) mView.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }


    // This method is called in onCreate method to open up a alert box and specifying the listeners on buttons in alert box.....
    private void openAlertBox(String heading1, String purpose, final String key, String current, String heading2){

        View mView = getLayoutInflater().inflate(R.layout.change_fields,null);

        TextView heading = (TextView) mView.findViewById(R.id.heading);
        heading.setText(heading1);

        TextView help = (TextView) mView.findViewById(R.id.help);
        help.setText(purpose);

        TextView field_heading = (TextView) mView.findViewById(R.id.field_heading);
        field_heading.setText(heading1);

        TextView currentValue = (TextView) mView.findViewById(R.id.current_value);
        currentValue.setText(current);

        TextView new_value = (TextView) mView.findViewById(R.id.new_value);
        new_value.setText(heading2);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this);
        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();

        final EditText newValue = (EditText) mView.findViewById(R.id.new_field_value);
        newValue.setHint("Enter new command");

        Button submit = (Button) mView.findViewById(R.id.submit1);
        Button cancel = (Button) mView.findViewById(R.id.cancel1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!newValue.getText().toString().isEmpty()){
                    changeValue(newValue.getText().toString(),key);
                    Toast.makeText(SettingActivity.this, "Changes are made successfully.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SettingActivity.this,SettingActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(SettingActivity.this, "Please fill field.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    // this method is called to change values of the fields and is called on clicking submit button in alert box....
    private void changeValue(String s, String key){

        SharedPreferences mshared = getSharedPreferences("Main", MODE_PRIVATE);
        SharedPreferences.Editor editor = mshared.edit();
        editor.putString(key,s);
        editor.commit();

    }
}