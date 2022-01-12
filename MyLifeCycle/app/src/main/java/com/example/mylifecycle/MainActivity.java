package com.example.mylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "onCreate 호출됨", Toast.LENGTH_LONG).show();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart 호출됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop 호출됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy 호출됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onResume 호출됨", Toast.LENGTH_LONG).show();
        SharedPreferences pref =  getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name","소녀시대");
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume 호출됨", Toast.LENGTH_LONG).show();
        SharedPreferences pref =  getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE);
        if(pref != null){
            String name =  pref.getString("name","");
            Toast.makeText(this,"복구된 이름 : " + name , Toast.LENGTH_LONG).show();
        }
    }
}