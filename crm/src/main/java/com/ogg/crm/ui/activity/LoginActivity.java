package com.ogg.crm.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.User;
import com.ogg.crm.network.logic.UserLogic;
import com.ogg.crm.ui.view.AutoClearEditText;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.UserInfoManager;

/**
 * 登录界面
 */
public class LoginActivity extends Activity implements OnClickListener,
        TextWatcher {
    public static final String ORIGIN_FROM_SPL_KEY = "com.spl";

    public static final String ORIGIN_FROM_REG_KEY = "com.reg";

    public static final String ORIGIN_FROM_USER_KEY = "com.user";

    private Context mContext;

    private ImageView mBackIv;
    private ImageView mSeePwdIv;


    private RelativeLayout mAccountRl;
    private RelativeLayout mPassWordRl;

    private AutoClearEditText mAccountEt;
    private AutoClearEditText mPassWordEt;
    private CheckBox mRemberpswCb;
    // private LinearLayout layoutProcess;
    private Button mLoginBtn;

    private LinearLayout mRegisterLl;
    private LinearLayout mForgetPwdLl;

    private boolean isShowPwd = false;

    private String mAccount;
    private String mPassWord;

    private User mUser = new User();

    private CustomProgressDialog mProgressDialog;

    private String mNowAction = ORIGIN_FROM_SPL_KEY;

    // 登陆装填提示handler更新主线程，提示登陆状态情况
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case UserLogic.LOGIN_SUC: {
                    if (null != msg.obj) {
                        mUser = (User) msg.obj;
                        mUser.setLogonPass(mPassWord);
                        UserInfoManager.setRememberPwd(mContext, true);
                        UserInfoManager.saveUserInfo(LoginActivity.this, mUser);
                        UserInfoManager.setUserInfo(LoginActivity.this);
                        UserInfoManager.setLoginIn(LoginActivity.this, true);

                        handle();
                    }

                    break;
                }
                case UserLogic.LOGIN_FAIL: {
                    Toast.makeText(mContext, R.string.login_fail,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                case UserLogic.LOGIN_EXCEPTION: {
                    break;
                }
                case UserLogic.NET_ERROR: {
                    break;
                }

                default:
                    break;
            }

            if (null != mProgressDialog && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = LoginActivity.this;
        initView();
        initData();

    }

    protected void initView() {

        mRegisterLl = (LinearLayout) findViewById(R.id.login_reg_ll);
        mForgetPwdLl = (LinearLayout) findViewById(R.id.login_forget_pwd_ll);
        mRegisterLl.setOnClickListener(this);
        mForgetPwdLl.setOnClickListener(this);

        mAccountRl = (RelativeLayout) findViewById(R.id.login_username_rl);
        mPassWordRl = (RelativeLayout) findViewById(R.id.login_password_rl);

        mAccountEt = (AutoClearEditText) findViewById(R.id.login_username);
        mPassWordEt = (AutoClearEditText) findViewById(R.id.login_password);
        mAccountEt.addTextChangedListener(this);
        mPassWordEt.addTextChangedListener(this);

        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
        mLoginBtn.setClickable(false);

        mSeePwdIv = (ImageView) findViewById(R.id.login_see_pwd_iv);
        mSeePwdIv.setOnClickListener(this);

        mAccountEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    clearBackground();
                    mAccountRl.setBackgroundResource(R.drawable.corners_bg_blue);
                    mPassWordEt.setClearIconVisible(false);
                    mAccountEt.setClearIconVisible(mAccountEt.getText().length() > 0);
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });

        mPassWordEt
                .setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            clearBackground();
                            mPassWordRl
                                    .setBackgroundResource(R.drawable.corners_bg_blue);
                            mAccountEt.setClearIconVisible(false);
                            mPassWordEt.setClearIconVisible(mPassWordEt.getText().length() > 0);
                            // 此处为得到焦点时的处理内容
                        } else {
                            // 此处为失去焦点时的处理内容
                        }
                    }
                });

    }

    private void initData() {
        mNowAction = getIntent().getAction();

        if (UserInfoManager.getRememberPwd(mContext)) {
            UserInfoManager.setUserInfo(mContext);

            mAccountEt.setText(UserInfoManager.userInfo.getLogonName());
            mPassWordEt.setText(UserInfoManager.userInfo.getLogonPass());
        }

    }


    private void clearBackground() {
        mAccountRl.setBackgroundResource(R.drawable.corners_bg_gray);
        mPassWordRl.setBackgroundResource(R.drawable.corners_bg_gray);
    }

    private void login() {
        // 获取用户的登录信息，连接服务器，获取登录状态
        mAccount = mAccountEt.getText().toString().trim();
        mPassWord = mPassWordEt.getText().toString().trim();

        if ("".equals(mAccount) || "".equals(mPassWord)) {
            Toast.makeText(LoginActivity.this,
                    mContext.getString(R.string.login_emptyname_or_emptypwd),
                    Toast.LENGTH_SHORT).show();
        } else {
            mProgressDialog = new CustomProgressDialog(mContext);
            mProgressDialog.show();

            User user = new User();
            user.setLogonName(mAccount);
            user.setLogonPass(mPassWord);
            UserLogic.login(mContext, mHandler, user);
        }
    }

    private void handle() {
        if (mNowAction.equals(ORIGIN_FROM_SPL_KEY)) {
            Toast.makeText(mContext, "登陆成功!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        } else if (mNowAction.equals(ORIGIN_FROM_REG_KEY)) {

        } else if (mNowAction.equals(ORIGIN_FROM_USER_KEY)) {
            LoginActivity.this.finish();
            overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @SuppressLint("NewApi")
    @Override
    public void afterTextChanged(Editable s) {
        mAccount = mAccountEt.getText().toString().trim();
        mPassWord = mPassWordEt.getText().toString().trim();

        if (!TextUtils.isEmpty(mAccount) && !TextUtils.isEmpty(mPassWord)) {
            mLoginBtn.setClickable(true);
            mLoginBtn.setBackgroundResource(R.drawable.corners_bg_blue_all);
        } else {
            mLoginBtn.setClickable(false);
            mLoginBtn.setBackgroundResource(R.drawable.corners_bg_gray_all);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn: {
                login();
                break;
            }

            case R.id.login_reg_ll: {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }
            case R.id.login_forget_pwd_ll: {
                Intent intent = new Intent(LoginActivity.this,
                        ForgetPwdActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }
            case R.id.login_see_pwd_iv: {
                if (!isShowPwd) {
                    mPassWordEt
                            .setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());
                    isShowPwd = !isShowPwd;
                } else {
                    mPassWordEt
                            .setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());
                    isShowPwd = !isShowPwd;
                }
                break;
            }

            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            LoginActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
