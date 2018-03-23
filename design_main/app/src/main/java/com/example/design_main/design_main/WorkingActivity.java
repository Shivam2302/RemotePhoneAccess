package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.design_main.design_main.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class WorkingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);

        ArrayList<Word> word = new ArrayList<Word>();

        word.add(new Word("Remote access","To access your phone, first you have to enable Remote " +
                "Access from General Settings. Now you cannot uninstall app until you uncheck remote access " +
                "checkbox.\n\nTo get information, You have to send a sms from some other phone which must contains your " +
                "primary password and command. If password and format of msg is correct information will be given to that " +
                "number automatically by our app."));

        word.add(new Word("Get Contact","If you want to call any of your contact but left your phone at home. You can get" +
                " contact by sending a msg as follows.\n\n" +
                "##MyHelper 12345 GetContact Ali\n\n" +
                "Assuming 12345 is primary password of your app and contact with name Ali is present in your phone."));

        word.add(new Word("Get notification","If you want to get all the unread notification from your phone, You can get them as " +
                "follows.\n\n" +
                "##MyHelper 12345 Notify"));

        word.add(new Word("Change Mode","If you want to change mode of your phone, you can do it as follows." +
                "\n\n##MyHelper 12345 ChangeMode\n\n" +
                "Above sms will change mode either from silent to general or from general to silent."));

        word.add(new Word("Get Location","In order to get current Gps location of your phone, you have to send sms as follows." +
                "\n\n##MyHelper 12345 GetLocation" +
                "\n\nAbove msg will give lattitude and longitude of location of your phone."));

        word.add(new Word("Lock Your phone","There are two ways to lock your phone.\n\n" +
                "Using pin already applied on your phone. This can be done as follows.\n\n" +
                "##MyHelper 12345 Lock\n\n" +
                "Using new pin to lock your phone. This can be done as follows.\n\n" +
                "##MyHelper 12345 Lock 81234567\n\n" +
                "where 81234567 is new pin."));

        word.add(new Word("Format","Suppose someone has stolen your phone. No need to worry, you can delete data from your phone as follows." +
                "\n\n##MyHelper 12345 Format"));

        WordAdapter itemsAdapter = new WordAdapter(this, word);

        ListView layout = (ListView) findViewById(R.id.list_in_working);

        layout.setAdapter(itemsAdapter);

    }
}