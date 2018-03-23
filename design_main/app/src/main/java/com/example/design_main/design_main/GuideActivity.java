package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.design_main.design_main.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        ArrayList<Word> word = new ArrayList<Word>();

        word.add(new Word("Step 1","Go to General Settings and enable Remote access to allow other devices to access your phone."));
        word.add(new Word("Step 2","Set Email address to get alert when any device will try to access your phone remotely. "));
        word.add(new Word("Step 3","Have a look at My Commands and try to remember these commands as these commands will be used to get information" +
                ". You can also change these commands anytime "));
        word.add(new Word("Step 4","Consider a scenario that you forgot your mobile phone at your home. No need to worry as you can operate your " +
                "phone using your friends phone."));
        word.add(new Word("Step 5","Now, in order to get information from your phone, you have to send sms from your friends phone" +
                " in given format\n ##<app_name><space><security_code><space><feature_code><space><information_required>"));
        word.add(new Word("Step 6","In above point security code is your primary password and feature code is commands for given action " +
                "and information is the arguments required for given command e.g contactName is required" +
                " for fetching contact"));


        WordAdapter itemsAdapter = new WordAdapter(this, word);

        ListView layout = (ListView) findViewById(R.id.list_in_guide);

        layout.setAdapter(itemsAdapter);
    }

}