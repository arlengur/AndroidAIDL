package ru.arlen.androidaidl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class AidlService extends Service {
    public static final String COMMON_PREF = "settings";
    public static final String PREF_NAME = "text";
    private SharedPreferences mSettings;

    public class MyBinder extends Binder {
        AidlService getService() {
            return AidlService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSettings = getSharedPreferences(COMMON_PREF, Context.MODE_PRIVATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IDataInterface.Stub() {
            @Override
            public void saveText(String text) throws RemoteException {
                SharedPreferences.Editor edit = mSettings.edit();
                edit.putString(PREF_NAME, text);
                edit.apply();
            }

            @Override
            public String loadText() throws RemoteException {
                return mSettings.getString(PREF_NAME, "");
            }
        };
    }
}
