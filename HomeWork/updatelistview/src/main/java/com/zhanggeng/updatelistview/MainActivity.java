package com.zhanggeng.updatelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private LinearLayout linearLayout;
    private ListView listView;
    private EditText editText;

    private ArrayList<String> strings = new ArrayList<>();

    private ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.tv);

        editText = (EditText) findViewById(R.id.edt);

        linearLayout = (LinearLayout) findViewById(R.id.ll);
        linearLayout.setOnClickListener(this);

        adapter = new ArrayAdapter<String>(this , R.layout.item , strings);
        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {

        if(editText.getText().toString().trim().equals("")){
            Toast.makeText(MainActivity.this, "不能为空，请重新输入" +
                    "", Toast.LENGTH_SHORT).show();
            return ;
        }

        switch (v.getId()){
            case  R.id.ll:
                String temp = editText.getText().toString();
                textView.setText(temp + "is learning android development!");

                strings.add(temp);

                adapter.notifyDataSetChanged();

                break;
        }

    }



}
