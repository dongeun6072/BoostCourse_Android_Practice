package com.example.myserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class chatService extends Service { //서비스에서 쓰레드를 실행함! 액티비티에 적으면 시스템 리소스가 작아서? 시스템이 액티비티 강제 종료 -> 서버 죽음때문에 
    public chatService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ServerThread thread = new ServerThread();
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class ServerThread extends Thread{
        public void run(){
            int port = 5001;

            try {
                ServerSocket server = new ServerSocket(port);
                Log.d("ServerThread","서버가 실행됨.");

                while(true){
                    Socket socket = server.accept(); //대기하다가 클라이언트 들어오는거 받음
                    ObjectInputStream inputStream =  new ObjectInputStream(socket.getInputStream()); //stream 파이프처럼 생각
                    Object input = inputStream.readObject();   //클라이언트에서 온 객체를 읽어볼 수 있음
                    Log.d("ServerThread", "input : " + input);

                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(input + "from server.");
                    outputStream.flush(); //버퍼에 남아있는 것 정리
                    Log.d("ServerThread", "output 보냄");
                    socket.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}