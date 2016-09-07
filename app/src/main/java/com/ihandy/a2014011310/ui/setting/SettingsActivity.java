
package com.ihandy.a2014011310.ui.setting;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.CONSTANT;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.support.Utils;
import com.ihandy.a2014011310.ui.support.SwipeBackActivity;

public class SettingsActivity extends SwipeBackActivity implements SensorEventListener {

    private Toolbar toolbar;
    private int mLang = -1;
    private SensorManager mSensorManager;

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
        Settings.swipeID = Settings.getInstance().getInt(Settings.SWIPE_BACK,0);

        setContentView(R.layout.activity_settings);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.setting);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.framelayout,new SettingsFragment()).commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
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

        if(Settings.isShakeMode == false){
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
