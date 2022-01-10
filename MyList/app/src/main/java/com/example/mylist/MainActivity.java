package com.example.mylist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        ListView listView =  (ListView)findViewById(R.id.listView);
        EditText editText = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);


        adapter = new SingerAdapter();
        adapter.addItem(new SingerItem("소녀시대","010-1000-1000",R.drawable.ic_launcher_foreground));
        adapter.addItem(new SingerItem("걸스데이","010-2000-2000",R.drawable.ic_launcher_foreground));
        adapter.addItem(new SingerItem("여자친구","010-3000-3000",R.drawable.ic_launcher_foreground));
        adapter.addItem(new SingerItem("티아라","010-4000-4000",R.drawable.ic_launcher_background));
        adapter.addItem(new SingerItem("애프터스쿨","010-5000-5000",R.drawable.ic_launcher_background));

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(),"선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                String telePhone = editText2.getText().toString();
                adapter.addItem(new SingerItem(name,telePhone,R.drawable.ic_launcher_background));
                adapter.notifyDataSetChanged(); //이것을 호출하면 어댑터에서 리스뷰쪽으로 갱신하라고 알려줌
            }
        });


    }

    class SingerAdapter extends BaseAdapter{
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(SingerItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingerItemView view = new SingerItemView(getApplicationContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setImage(item.getResId());


            return view;
        }
    }
}