package com.geekband.moran.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.geekband.moran.interfaces.BaseViewInterface;

import butterknife.ButterKnife;

/**
 * baseActionBar Activity
 */
public abstract class BaseActivity extends Activity implements
        View.OnClickListener, BaseViewInterface {

    public static final String INTENT_ACTION_EXIT_APP = "INTENT_ACTION_EXIT_APP";

    private boolean _isVisible;

    protected LayoutInflater mInflater;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        TDevice.hideSoftKeyboard(getCurrentFocus());
        ButterKnife.unbind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mInflater = getLayoutInflater();

        // 通过注解绑定控件
        ButterKnife.bind(this);

        init(savedInstanceState);
        initView();
        initData();
        _isVisible = true;
    }

    protected void onBeforeSetContentLayout() {
    }


    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }



    protected void init(Bundle savedInstanceState) {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}

