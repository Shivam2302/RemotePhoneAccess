package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.design_main.design_main.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        TextView setting = (TextView) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(i);
            }
        });


        TextView myCommands = (TextView) findViewById(R.id.commands);
        myCommands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,CommandsActivity.class);
                startActivity(i);
            }
        });


        TextView working = (TextView) findViewById(R.id.working);
        working.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,WorkingActivity.class);
                startActivity(i);
            }
        });


        TextView guide = (TextView) findViewById(R.id.guide);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,GuideActivity.class);
                startActivity(i);
            }
        });

        TextView about = (TextView) findViewById(R.id.about_us);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,AboutUs.class);
                startActivity(i);
            }
        });

    }
}