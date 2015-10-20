package com.zhanggeng.courtcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_a_counter, tv_b_counter;

    private Button btn_a_3, btn_a_2, btn_a_free, btn_b_3, btn_b_2, btn_b_free, btn_reset;

    private int num_a , num_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_a_counter = (TextView) findViewById(R.id.tv_a_counter);
        tv_b_counter = (TextView) findViewById(R.id.tv_b_counter);


        btn_a_3 = (Button) findViewById(R.id.btn_a_3);
        btn_a_3.setOnClickListener(this);

        btn_a_2 = (Button) findViewById(R.id.btn_a_2);
        btn_a_2.setOnClickListener(this);

        btn_a_free = (Button) findViewById(R.id.btn_a_free);
        btn_a_free.setOnClickListener(this);

        btn_b_3 = (Button) findViewById(R.id.btn_b_3);
        btn_b_3.setOnClickListener(this);

        btn_b_2 = (Button) findViewById(R.id.btn_b_2);
        btn_b_2.setOnClickListener(this);

        btn_b_free = (Button) findViewById(R.id.btn_b_free);
        btn_b_free.setOnClickListener(this);


        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_a_3:
                add_num(0 , 3 , false);
                break;
            case R.id.btn_a_2:
                add_num(0 , 2 , false);
                break;
            case R.id.btn_a_free:
                add_num(0 , 0 , false);
                break;
            case R.id.btn_b_3:
                add_num(1 , 3 , false);
                break;
            case R.id.btn_b_2:
                add_num(1 , 2 , false);
                break;
            case R.id.btn_b_free:
                add_num(1 , 0 , false);
                break;
            case R.id.btn_reset:
                add_num(0 , 0 , true);
                add_num(1 , 0 , true);
                break;

        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("a", tv_a_counter.getText().toString());
        outState.putString("b" , tv_b_counter.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        tv_a_counter.setText((String) savedInstanceState.get("a"));
        tv_b_counter.setText((String) savedInstanceState.get("b"));

    }

    private void add_num(int  which , int num , boolean isReset){

        if (isReset){
            tv_a_counter.setText("0");
            tv_b_counter.setText("0");

            return;
        }

        if(which == 0){
            tv_a_counter.setText(Integer.parseInt(tv_a_counter.getText().toString()) + num + "");

        }else{
            tv_b_counter.setText(Integer.parseInt(tv_b_counter.getText().toString()) + num + "");
        }

    }
}
