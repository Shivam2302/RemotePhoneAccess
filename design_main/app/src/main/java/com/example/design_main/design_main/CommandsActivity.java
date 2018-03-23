package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.design_main.design_main.R;

public class CommandsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);

        LinearLayout disable = (LinearLayout) findViewById(R.id.access);
        LinearLayout contact = (LinearLayout) findViewById(R.id.contact);
        LinearLayout notification = (LinearLayout) findViewById(R.id.notfication);
        LinearLayout mode = (LinearLayout) findViewById(R.id.change_mode);
        LinearLayout location = (LinearLayout) findViewById(R.id.get_location);
        LinearLayout lock = (LinearLayout) findViewById(R.id.lock_phone);
        LinearLayout format = (LinearLayout) findViewById(R.id.format);

        SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);


        // fetching values from the database and showing it on the command page.....
        TextView command1 = (TextView) findViewById(R.id.current_command1);
        command1.setText(mShared.getString("access","-1"));

        TextView command2 = (TextView) findViewById(R.id.current_command2);
        command2.setText(mShared.getString("contact","-1"));

        TextView command3 = (TextView) findViewById(R.id.current_command3);
        command3.setText(mShared.getString("notification","-1"));

        TextView command4 = (TextView) findViewById(R.id.current_command4);
        command4.setText(mShared.getString("mode","-1"));

        TextView command5 = (TextView) findViewById(R.id.current_command5);
        command5.setText(mShared.getString("location","-1"));

        TextView command6 = (TextView) findViewById(R.id.current_command6);
        command6.setText(mShared.getString("lock","-1"));

        TextView command7 = (TextView) findViewById(R.id.current_command7);
        command7.setText(mShared.getString("format","-1"));

        // on clicking disable field an alert box is opened that gives us a chance to change command.....
        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);
                String purpose = "This command is used to disable the functioning of the app.";
                String current = mShared.getString("access","-1");
                String key = "access";

                openAlertBox(purpose,key,current);
            }

        });


        // on clicking contact field an alert box is opened that gives us a chance to change command.....
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);
                String purpose = "This command is used to fetch contacts from your phone remotly.";
                String current = mShared.getString("contact","-1");
                String key = "contact";

                openAlertBox(purpose,key,current);

            }

        });


        // on clicking notification field an alert box is opened that gives us a chance to change command.....
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);
                String purpose = "This command is used to get notifications from your phone remotly.";
                String current = mShared.getString("notification","-1");
                String key = "notification";

                openAlertBox(purpose,key,current);
            }

        });


        // on clicking change mode field a alert box is opened that gives us a chance to change command.....
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);
                String purpose = "This command is used to change mode from silent to general or vice versa.";
                String current = mShared.getString("mode","-1");
                String key = "mode";

                openAlertBox(purpose,key,current);

            }

        });


        // on clicking Gps location field a alert box is opened that gives us a chance to change command.....
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);
                String purpose = "This command is used to get Gps location of your phone.";
                String current = mShared.getString("location","-1");
                String key = "location";

                openAlertBox(purpose,key,current);
            }

        });


        // on clicking lock field a alert box is opened that gives us a chance to change command.....
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);
                String purpose = "This command is used to lock your phone using existing password or by giving new password.";
                String current = mShared.getString("lock","-1");
                String key = "lock";

                openAlertBox(purpose,key,current);
            }

        });


        // on clicking format phone field a alert box is opened that gives us a chance to change command.....
        format.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences mShared = getSharedPreferences("Main", MODE_PRIVATE);
                String purpose = "This command is used to format your mobile phone in case you lost it.";
                String current = mShared.getString("format","-1");
                String key = "format";

                openAlertBox(purpose,key,current);
            }

        });

    }

    // This method is called in onCreate method to open up a alert box and specifying the listeners on buttons in alert box.....
    private void openAlertBox(String purpose, final String key , String current){

        View mView = getLayoutInflater().inflate(R.layout.change_fields,null);

        TextView heading = (TextView) mView.findViewById(R.id.heading);
        heading.setText("Change Command");

        TextView help = (TextView) mView.findViewById(R.id.help);
        help.setText(purpose);

        TextView field_heading = (TextView) mView.findViewById(R.id.field_heading);
        field_heading.setText("Current Command");

        TextView currentValue = (TextView) mView.findViewById(R.id.current_value);
        currentValue.setText(current);

        TextView new_value = (TextView) mView.findViewById(R.id.new_value);
        new_value.setText("New Command");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CommandsActivity.this);
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
                    Toast.makeText(CommandsActivity.this, "Command is changed successfully.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CommandsActivity.this,CommandsActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(CommandsActivity.this, "Please fill field.", Toast.LENGTH_SHORT).show();
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