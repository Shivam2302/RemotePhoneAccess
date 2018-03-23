package com.example.design_main.design_main;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.example.design_main.design_main.R;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by User on 12/15/2017.
 */

public class MyService extends Service {

    private static final int NOTIFICATION_ID = 543 ;

    ArrayList<String> StoreContacts, StoreSms, StoreMissedCalls ;

    String name, phonenumber, number, body, password ;
    AudioManager am;

    Context context;
    PolicyManager policyManager;


    public static boolean isServiceRunning = false;


    class ServiceActivity extends Activity{
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            if(resultCode == Activity.RESULT_OK && requestCode == PolicyManager.request_code){

            }
            else{
                super.onActivityResult(requestCode,resultCode,data);
            }
        }
    }


    ServiceActivity activity;
    MyService(ServiceActivity activity){
        this.activity=activity;
    }

    public MyService() {
        // No args constructor
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceWithNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            startServiceWithNotification();

            SharedPreferences prefs = getSharedPreferences("Main", MODE_PRIVATE);
            password = prefs.getString("primaryPassword", "No name defined");

            Toast.makeText(MyService.this,"service has started",Toast.LENGTH_SHORT).show();

        }
        else stopMyService();
        return START_STICKY;
    }


    // In case the service is deleted or crashes some how
    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }



    //    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    void startServiceWithNotification() {

        if (isServiceRunning) {
            return;
        }

        isServiceRunning = true;

        context = getBaseContext();

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        StoreContacts = new ArrayList<String>();
        StoreSms = new ArrayList<String>();
        StoreMissedCalls = new ArrayList<String>();
        policyManager = new PolicyManager(this);

        final Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {

            // @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                if (Looper.myLooper() == null)
                {
                    Looper.prepare();
                }

//                Toast.makeText(MyService.this, "service for sms", Toast.LENGTH_SHORT).show();
                GetSmsIntoArrayList();
                handler.postDelayed(this, 45000);

                Looper.loop();
            }
        };

        handler.postDelayed(runnable, 45000);

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("MyHelper")
                .setTicker("MyHelper")
                .setContentText("service is running")
                .setContentIntent(contentPendingIntent)
                .setOngoing(true)
                .build();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;     // NO_CLEAR makes the notification stay when the user performs a "delete all" command
        startForeground(NOTIFICATION_ID, notification);
    }

    void stopMyService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }


    // @RequiresApi(api = Build.VERSION_CODES.O)
    public void GetSmsIntoArrayList() {

        // fetching data from inbox for checking required msg ...
        Cursor cursor = getContentResolver().query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, null);

        String date = "";

        if(cursor!=null){

            while (cursor.moveToNext()) {

                body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY));
                number = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));

                String temp[] = body.split(" ");
//              if(cursor.getString(cursor.getColumnIndex((Telephony.Sms.Inbox.))))

                if(temp[0].equals("##APP")){

                    Toast.makeText(MyService.this,password+" before check",Toast.LENGTH_SHORT).show();

                    if(temp[1].compareTo(password)==0) {

                        if (temp[2].equals("getContacts")) {

                            String reqContact = GetContactsIntoArrayList(temp[3]);
                            Toast.makeText(MyService.this,"Running get contact",Toast.LENGTH_SHORT).show();
                            SmsManager.getDefault().sendTextMessage(number, null, reqContact, null, null);
                        }

                        else if (temp[2].equals("changeToSilent")) {

                             am.setRingerMode(0);
                             Toast.makeText(MyService.this,"Running change silent",Toast.LENGTH_SHORT).show();
                        }

                        else if (temp[2].equals("changeToNormal")) {

                            am.setRingerMode(2);
                            Toast.makeText(MyService.this,"Running change normal",Toast.LENGTH_SHORT).show();
                        }

                        else if (temp[2].equals("getMissedCalls")) {

                            Toast.makeText(MyService.this,"Running missed calls",Toast.LENGTH_SHORT).show();
                            GetMissedCalls();

                            if(StoreMissedCalls.size() == 0) {

                                Toast.makeText(MyService.this, "no calls", Toast.LENGTH_SHORT).show();
                            }

                            else {

                                int i = 0;
                                String missedCall = "";
                                while (i < StoreMissedCalls.size()) {

                                    missedCall += StoreMissedCalls.get(i) + "\n";
                                    i++;
                                }

                                SmsManager.getDefault().sendTextMessage(number, null, missedCall, null, null);
                            }
                        }

                        else if (temp[2].equals("wipeData")) {

                            if (!policyManager.isAdminActive()) {

                                Intent activateDeviceAdmin = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                                activateDeviceAdmin.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "after admin you will block");
                                activity.startActivityForResult(activateDeviceAdmin, PolicyManager.request_code);
                            }

                            policyManager.removeData();
                        }

                        else if (temp[2].compareTo("LockNow") == 0) {

                            if (!policyManager.isAdminActive()) {

                                Intent activateDeviceAdmin = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                                activateDeviceAdmin.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "after admin you will block");
                                activity.startActivityForResult(activateDeviceAdmin, PolicyManager.request_code);
                            }

                            if(temp.length==2){

                                Toast.makeText(MyService.this,"Running lock now",Toast.LENGTH_SHORT).show();
                                policyManager.lock();
                            }

                            else if(temp.length==3){

                                Toast.makeText(MyService.this,"Running lock now",Toast.LENGTH_SHORT).show();
                                policyManager.changePasswordAndLock(temp[3], true);
                            }

                        }
                    }
                }
            }
            cursor.close();
        }
    }

    public String getDateTime(long timestamp) {

        try{

            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }

        catch (Exception e) {

        }

        return "-1";
    }


    public String GetContactsIntoArrayList(String contactName){

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        if(cursor!=null){

            while (cursor.moveToNext()) {

                name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                if (name.equals(contactName)) {

                    phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    return phonenumber;
                }
            }

            cursor.close();
        }

        return "Not found";
    }

    public void GetMissedCalls() {

        final String[] projection = null;
        final String selection = null;
        final String[] selectionArgs = null;
        final String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";

        String unreadWhere = CallLog.Calls.TYPE + "=" +   CallLog.Calls.MISSED_TYPE    + " AND " +    CallLog.Calls.IS_READ + "=0";
        String[] unreadProjection =  {CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.NEW, CallLog.Calls.DATE};

        Cursor cursor1 = this.getContentResolver().query(CallLog.Calls.CONTENT_URI, unreadProjection, unreadWhere, null, sortOrder);

        if(cursor1!=null){

            while (cursor1.moveToNext()) {

                String callNumber = cursor1.getString(0);
                String callDate = cursor1.getString(3);

                String dateTime = getDateTime(Long.parseLong(callDate));

                StoreMissedCalls.add(callNumber + " " + dateTime);
            }
        }
        cursor1.close();
    }

}