
package com.ihandy.a2014011310.ui.category;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.CONSTANT;
import com.ihandy.a2014011310.support.HttpUtil;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.support.Utils;
import com.ihandy.a2014011310.ui.setting.SettingsFragment;
import com.ihandy.a2014011310.ui.support.SwipeBackActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class CategoryActivity extends SwipeBackActivity implements SensorEventListener {

    private Toolbar toolbar;
    private SensorManager mSensorManager;
    private boolean isShakeMode = false;
    private int mLang = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Language
        mLang = Utils.getCurrentLanguage();
        if (mLang > -1) {
            Utils.changeLanguage(this, mLang);
        }

        //set Theme
        if(Settings.isNightMode){
            this.setTheme(R.style.NightTheme);
        }else{
            this.setTheme(R.style.DayTheme);
        }

        setContentView(R.layout.activity_category);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.category));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        getFragmentManager().beginTransaction().replace(R.id.framelayout,new CategoryFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        isShakeMode = Settings.getInstance().getBoolean(Settings.SHAKE_TO_RETURN,true);
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(isShakeMode == false){
            return;
        }

        float value[] = event.values;
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if((Math.abs(value[0]) + Math.abs(value[1]) + Math.abs(value[2]))>CONSTANT.shakeValue){
                onBackPressed();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
