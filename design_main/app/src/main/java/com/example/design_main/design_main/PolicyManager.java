package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PolicyManager {
    public static final int request_code = 101;
    private Context mContext;
    private DevicePolicyManager mDPM;
    private ComponentName mComponent;
    public PolicyManager(Context context){
        this.mContext = context;
        mDPM = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponent = new ComponentName(mContext.getPackageName(),mContext.getPackageName() + ".myDeviceAdminReceiver");
    }

    public boolean isAdminActive(){
        return  mDPM.isAdminActive(mComponent);
    }


    public void lock(){
        mDPM.lockNow();
    }

    public void removeData(){
        mDPM.wipeData(0);
    }

    public void changePasswordAndLock(String newPass, boolean lock){
        
        if (mDPM.isAdminActive(mComponent)) {
            Log.e("admin active", "Locking device with password");
            mDPM.setPasswordQuality(mComponent, DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED);
            mDPM.setPasswordMinimumLength(mComponent, 4);
            mDPM.resetPassword(newPass, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
            mDPM.lockNow();
        }
        else{
            Log.e("admin not active", "Locking device with password");
        }
    }
}
