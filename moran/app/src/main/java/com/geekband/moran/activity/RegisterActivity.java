package com.geekband.moran.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geekband.moran.R;
import com.geekband.moran.api.HttpManager;
import com.geekband.moran.base.BaseActivity;
import com.geekband.moran.utils.L;
import com.geekband.moran.utils.NetUtil;
import com.geekband.moran.utils.ToastTool;
import com.geekband.moran.utils.UIUtils;
import com.geekband.moran.utils.Validator;
import com.geekband.moran.utils.VolleyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.edt_register_username)
    EditText edt_username;

    @Bind(R.id.edt_register_email)
    EditText edt_email;

    @Bind(R.id.edt_register_pwd)
    EditText edt_pwd;

    @Bind(R.id.edt_register_pwd_again)
    EditText edt_pwd_again;

    @Bind(R.id.btn_register)
    Button btn_register;

    @Bind(R.id.tv_register_login)
    TextView tv_register_login;

    private Context context = this;

    private String username;
    private String email;
    private String pwd;
    private String pwd_again;

    private static RegisterActivity mInstance;


    public static RegisterActivity getInstance() {
        if (mInstance == null) {

            synchronized (LoginActivity.class) {

                if (mInstance == null) {
                    mInstance = new RegisterActivity();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

        btn_register.setOnClickListener(this);
        tv_register_login.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_login:

                UIUtils.startActivity(context, LoginActivity.class);
                break;

            case R.id.btn_register:

                if (NetUtil.isNetworkAvailable(context)) if (validator()) {
                    //TODO: 发起网络请求
                    username = edt_username.getText().toString().trim();
                    email = edt_email.getText().toString().trim();
                    pwd = edt_pwd.getText().toString().trim();
                    pwd_again = edt_pwd_again.getText().toString().trim();


                    VolleyUtil.myCancleAll(context);

                    Map<String , String> map = new HashMap<>();
                    map.put("username" , username);
                    map.put("password" , pwd_again);
                    map.put("email" , email);
                    map.put("gbid", HttpManager.gid);

                    L.et("parm" , username + "==" + pwd_again + "==" + email + "===" + HttpManager.gid);

                    JSONObject jsonObject = new JSONObject(map);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST
                            , HttpManager.mGeekBandApi + HttpManager.mRegister, jsonObject
                            ,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    L.et("register_succ" , jsonObject.toString());
                                    try {
                                        if(jsonObject.getInt("status") == 1){
                                            L.et("status" , jsonObject.getInt("status")+"");
                                            ToastTool.MyToast(context, jsonObject.getString("message"));
                                        }else{
                                            ToastTool.MyToast(context , jsonObject.getString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    L.et("register_error" , volleyError.toString());
                                }
                            }
                    ){
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            HashMap<String, String> headers = new HashMap<String, String>();
//                            headers.put("Content-Type", "application/json; charset=utf-8");
//                            return headers;
//                        }
                    };

                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    jsonObjectRequest.setTag(this);
                    VolleyUtil.addRequest(context , jsonObjectRequest);

                } else {
                    ToastTool.MyToast(context, "网络不可用");
                }


                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        VolleyUtil.myCancleAll(context);

    }

    private boolean validator() {

        username = edt_username.getText().toString().trim();
        email = edt_email.getText().toString().trim();
        pwd = edt_pwd.getText().toString().trim();
        pwd_again = edt_pwd_again.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd_again) || TextUtils.isEmpty(username)) {
            ToastTool.MyToast(context, "用户名或邮箱或密码不能为空");
            return false;
        }

        if (!Validator.isUsername(username)) {
            ToastTool.MyToast(context, "用户名只能包含中文、英文、数字 且长度不超过20个字符");
            return false;
        }
        if (!Validator.isEmail(email)) {
            ToastTool.MyToast(context, "请输入正确的邮箱");
            return false;
        }

        if (!Validator.isPassword(pwd)) {
            ToastTool.MyToast(context, "密码只能包含数字和英文且长度在6~16之间");
            return false;
        }

        if (!pwd.equals(pwd_again)) {
            ToastTool.MyToast(context, "两次输入密码不一致");
            return false;
        }

        return true;

    }


}
