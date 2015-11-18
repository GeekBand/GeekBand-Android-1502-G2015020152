package com.geekband.moran.activity;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geekband.moran.R;
import com.geekband.moran.base.BaseActivity;
import com.geekband.moran.utils.UIUtils;

import butterknife.Bind;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.btn_login)
    Button login_btn;

    @Bind(R.id.tv_register)
    TextView register_tv_login;

    @Bind(R.id.edt_login_email)
    EditText edt_email;

    @Bind(R.id.edt_login_pwd)
    EditText edt_pwd;


    private static LoginActivity mInstance;

    private Context context = this;



    public static LoginActivity getInstance() {
        if (mInstance == null) {

            synchronized (LoginActivity.class) {

                if (mInstance == null) {
                    mInstance = new LoginActivity();
                }
            }
        }
        return mInstance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void initView() {

        login_btn.setOnClickListener(this);
        register_tv_login.setOnClickListener(this);


    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_register:

                UIUtils.startActivity(context , RegisterActivity.class);
                break;
        }

    }



}
