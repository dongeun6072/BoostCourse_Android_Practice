package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate() 호출됨.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand() 호출됨.");

        if(intent == null){
            return Service.START_STICKY;
        } else{
            processCommand(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent){
        String command = intent.getStringExtra("command");
        String name = intent.getStringExtra("name");
        Log.d(TAG,"전달받은 데이터 : " + command + "," + name);

        try{
            Thread.sleep(5000); //5초동안 쉰다.
        } catch (Exception e){};

        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
        showIntent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK|
                            intent.FLAG_ACTIVITY_SINGLE_TOP|
                            intent.FLAG_ACTIVITY_CLEAR_TOP);
        showIntent.putExtra("command","show");
        showIntent.putExtra("name",name + "from service.");
        startActivity(showIntent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy() 호출됨.");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}