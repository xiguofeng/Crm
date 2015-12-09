package com.ogg.crm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ogg.crm.R;
import com.ogg.crm.service.ConfigInfoService;
import com.ogg.crm.utils.UserInfoManager;

public class SplashActivity extends BaseActivity {

    private ImageView mSplashItemIv = null;

    private ImageView mSplashLogoIv = null;

    final int duration = 3 * 1000;

    protected Handler mHandler = null;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        mContext = SplashActivity.this;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        mHandler = new Handler(getMainLooper());
        findViewById();
        initView();

    }

    protected void findViewById() {
        mSplashItemIv = (ImageView) findViewById(R.id.splash_loading_item);
        mSplashLogoIv = (ImageView) findViewById(R.id.splash_logo_iv);
    }

//    private void showToggle() {
//
//    animate(mSplashLogoIv).rotationYBy(720);
//    ObjectAnimator.ofFloat(mSplashLogoIv, "translationY", 0, 80, -80, 0)
//    .setDuration(duration).start();
//
//    }

    @Override
    protected void initView() {
        Animation translate = AnimationUtils.loadAnimation(this,
                R.anim.splash_loading);
        translate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                //showToggle();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intentService = new Intent(mContext,
                        ConfigInfoService.class);
                mContext.startService(intentService);

                if (UserInfoManager.getLoginIn(SplashActivity.this)) {
                    UserInfoManager.setUserInfo(SplashActivity.this);
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.push_down_in,
                            R.anim.push_down_out);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.setAction(LoginActivity.ORIGIN_FROM_SPL_KEY);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.push_down_in,
                            R.anim.push_down_out);
                }
            }
        });
        mSplashItemIv.setAnimation(translate);
    }

}
