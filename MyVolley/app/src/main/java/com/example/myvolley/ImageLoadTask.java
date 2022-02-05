package com.example.myvolley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private String urlStr;
    private ImageView imageView;

    private static HashMap<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();


    public ImageLoadTask(String urlStr, ImageView imageView){
        this.urlStr = urlStr;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() { //밑으로 순서대로 실행
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = null;
        try {
            if(bitmapHash.containsKey(urlStr)){
                Bitmap oldbitmap = bitmapHash.remove(urlStr);
                if(oldbitmap !=null){
                    oldbitmap.recycle();
                    oldbitmap = null;
                }
            }

            URL url = new URL(urlStr); //url 객체 만듬
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            //이미지 파일이 큰데, 그것을 비트맵 객체로 만들면 메모리에 올라가는데 , 반복적으로 가지고오면 메모리가 차 outofMemory가 생길수있음..
            bitmapHash.put(urlStr,bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
    }

}
