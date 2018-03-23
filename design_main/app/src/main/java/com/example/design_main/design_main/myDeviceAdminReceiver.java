package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;


public class myDeviceAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
    }
}