package com.zhanggeng.homework;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_del, btn_add, btn_price;
    private TextView tv_num, tv_price;

    private int num_del, num_add, num_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_price = (TextView) findViewById(R.id.tv_price);

        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);

        btn_price = (Button) findViewById(R.id.btn_price);
        btn_price.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_del:
                del_price();
                break;
            case R.id.btn_add:
                add_price();
                break;
            case R.id.btn_price:
                alert_price();
                break;
        }
    }

    private void add_price() {

        num_add = Integer.parseInt(tv_num.getText().toString()) + 1;

        tv_num.setText(num_add + "");
        tv_price.setText("$" + num_add * 5);


    }

    private void del_price() {

        if (Integer.parseInt(tv_num.getText().toString()) <= 0) {
            Toast.makeText(MainActivity.this, "商品数不能小于0", Toast.LENGTH_SHORT).show();
            return;
        }

        num_del = Integer.parseInt(tv_num.getText().toString()) - 1;
        tv_num.setText(num_del + "");
        tv_price.setText("$" + num_del * 5);
    }

    private void alert_price() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认").setMessage("您好，您已经添加了 " + tv_num.getText() + " 件商品,总计  " + tv_price.getText())
                .setNegativeButton("cancle" , null)
                .setPositiveButton("sure" , null)
                .show();


    }


}
