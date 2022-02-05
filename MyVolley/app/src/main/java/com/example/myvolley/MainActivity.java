package com.example.myvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImageRequest();
            }
        });

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }

    public void sendRequest(){
       // String url = "https://www.google.co.kr";
        String url = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("응답 - > " + response);

                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 - > " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms = new HashMap<String,String>();
                return parms;
            }
        };
        request.setShouldCache(false); //이전결과있어도 새로운 결과 보여줌..
        AppHelper.requestQueue.add(request);
        println("요청 보냄.");
    }


    public void sendImageRequest(){
        String url = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fmovie-phinf.pstatic.net%2F20211116_32%2F1637030173883ONX7f_JPEG%2Fmovie_image.jpg";
        ImageLoadTask task = new ImageLoadTask(url,imageView);
        task.execute();
    }



    public void processResponse(String response){
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response,MovieList.class);
        if(movieList != null){
            int countMovie = movieList.boxOfficeResult.dailyBoxOfficeList.size();
            println("박스오피스 타입 : " + movieList.boxOfficeResult.boxofficeType);
            println("응답받은 영화 갯수 : " + countMovie);
        }
    }


    public void println(String data){
        textView.append(data + "\n");
    }

}



// Volley 라이브러리리
// Requset 객체만 만들어주고! , RequestQueue가 알아서 쓰레드를 만들어서 알아서 요청 응답을 한다.