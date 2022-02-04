package com.example.myasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        Button button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressTask task = new ProgressTask();
                task.execute("Start");
            }
        });

//        따라서 각각의 파라미터는 다음과 같이 전달된다.
//        - onCreate()
//        task.execute(String) → doInBackground(String)
//        -doInBackground()
//        publishProgress(Integer) → onProgressUpdate(Integer)
//        return value(Integer) → onPostExecute(Integer)


    }

    class ProgressTask extends AsyncTask<String, Integer, Integer> {

        int value = 0;

        @Override
        protected Integer doInBackground(String... strings) { //콜백메소드 자동으로 실행 , ...은 가변길이의 파라미터
            while(true){
                if(value >= 100)
                    break;

                value += 4;

                publishProgress(value); // onProgressUpdate메소드 실행

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0].intValue()); //values[0]이 도대체 뭐지?
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();
        }

    }

}