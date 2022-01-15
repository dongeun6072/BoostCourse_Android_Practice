package com.example.mysmsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public void onReceive(Context context, Intent intent) {
       Log.d(TAG,"onReceive() 호출됨.");
       Bundle bundle = intent.getExtras();
       SmsMessage[] messages = parseSmsMessage(bundle);

        if (messages.length > 0){
            String sender =  messages[0].getOriginatingAddress();
            Log.d(TAG,"sender : " + sender);

            String contents  = messages[0].getMessageBody().toString();
            Log.d(TAG,"contents : " + contents);

            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.d(TAG, "received date : " + receivedDate);

            sendToActiviy(context, sender,contents,receivedDate);
        }
    }

    private void sendToActiviy(Context context, String sender, String contents ,Date receivedDate){
        Intent intent = new Intent(context,SmsActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK|
                        intent.FLAG_ACTIVITY_SINGLE_TOP|
                        intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sender", sender);
        intent.putExtra("contents",contents);
        intent.putExtra("receivedDate", format.format(receivedDate));
        context.startActivity(intent);
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Object[] objs = (Object[])bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i = 0; i<objs.length; i++){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i],format);
            } else{
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
            }
        }
        return messages;
    }
}