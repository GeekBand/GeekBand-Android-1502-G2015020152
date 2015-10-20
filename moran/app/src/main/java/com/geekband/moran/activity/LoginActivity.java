package com.geekband.moran.activity;

import android.view.View;
import android.view.Window;

import com.geekband.moran.R;
import com.geekband.moran.base.BaseActivity;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
