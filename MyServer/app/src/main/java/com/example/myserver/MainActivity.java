package com.example.myserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ServerThread thread = new ServerThread();
//                thread.start();
                Intent intent = new Intent(getApplication(),chatService.class);
                startService(intent);
            }
        });
    }

//    class ServerThread extends Thread{ //시스템 리소스가 작으면? 시스템이 액티비티 강제 종료 -> 서버 죽음
//        public void run(){
//            int port = 5001;
//
//            try {
//                ServerSocket server = new ServerSocket(port);
//                Log.d("ServerThread","서버가 실행됨.");
//
//                while(true){
//                    Socket socket = server.accept(); //대기하다가 클라이언트 들어오는거 받음
//                    ObjectInputStream inputStream =  new ObjectInputStream(socket.getInputStream()); //stream 파이프처럼 생각
//                    Object input = inputStream.readObject();   //클라이언트에서 온 객체를 읽어볼 수 있음
//                    Log.d("ServerThread", "input : " + input);
//
//                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//                    outputStream.writeObject(input + "from server.");
//                    outputStream.flush(); //버퍼에 남아있는 것 정리
//                    Log.d("ServerThread", "output 보냄");
//                    socket.close();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}