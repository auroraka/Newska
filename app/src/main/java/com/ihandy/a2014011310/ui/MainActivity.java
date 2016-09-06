package com.ihandy.a2014011310.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ihandy.a2014011310.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.support.Utils;
import com.ihandy.a2014011310.support.CONSTANT;
//import com.ihandy.a2014011310.about.AboutActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private Toolbar toolbar;
    private Drawer drawer ;
    private AccountHeader header;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private  Fragment currentFragment ;
    private Menu menu;

    private int mLang = -1;

    private SensorManager mSensorManager;

    private boolean isShake = false;
    private long lastPressTime = 0;
    private Settings mSettings = Settings.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Language
        mLang = Utils.getCurrentLanguage();
        if (mLang > -1) {
            Utils.changeLanguage(this, mLang);
        }

        //Settings
        Settings.isShakeMode = mSettings.getBoolean(Settings.SHAKE_TO_RETURN, false);
        Settings.searchID = mSettings.getInt(Settings.SEARCH, 0);
        Settings.swipeID = mSettings.getInt(Settings.SWIPE_BACK,0);
        Settings.isAutoRefresh = mSettings.getBoolean(Settings.AUTO_REFRESH, false);
        Settings.isExitConfirm = mSettings.getBoolean(Settings.EXIT_CONFIRM, true);
        Settings.isNightMode = mSettings.getBoolean(Settings.NIGHT_MODE, false);
        Settings.noPicMode = mSettings.getBoolean(Settings.NO_PIC_MODE, false);

        // change Brightness
        if(mSettings.isNightMode && Utils.getSysScreenBrightness() > CONSTANT.NIGHT_BRIGHTNESS){
            Utils.setSysScreenBrightness(CONSTANT.NIGHT_BRIGHTNESS);
        }else if(mSettings.isNightMode == false && Utils.getSysScreenBrightness() == CONSTANT.NIGHT_BRIGHTNESS){
            Utils.setSysScreenBrightness(CONSTANT.DAY_BRIGHTNESS);
        }

        if(Settings.isNightMode){
            this.setTheme(R.style.NightTheme);
        }else{
            this.setTheme(R.style.DayTheme);
        }

        //show
        setContentView(R.layout.activity_main);
        initData();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //currentFragment = new DailyFragment();
        //switchFragment();

        //Utils.DLog("------version: "+Utils.getVersion());

    }

    private void initData(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        header = new AccountHeaderBuilder().withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(new ProfileDrawerItem().withIcon(R.drawable.logo)
                        .withEmail(getString(R.string.author_email))
                        .withName(getString(R.string.author_name)))
//                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//                    @Override
//                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
//                        Intent i = new Intent(MainActivity.this, AboutActivity.class);
//                        startActivity(i);
//                        return false;
//                    }
//                })
                .build();
//        drawer = new DrawerBuilder().withActivity(this)
//                .withToolbar(toolbar)
//                .withActionBarDrawerToggleAnimated(true)
//                .withAccountHeader(header)
//                .withSliderBackgroundColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.night_primary) : ContextCompat.getColor(this, R.color.white))
//                .addDrawerItems(
//                        new PrimaryDrawerItem().withName(R.string.daily).withIcon(R.mipmap.ic_home).withIdentifier(R.mipmap.ic_home)
//                                .withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white) : ContextCompat.getColor(this, R.color.text_color)),
//                        new PrimaryDrawerItem().withName(R.string.science).withIcon(R.mipmap.ic_science).withIdentifier(R.mipmap.ic_science)
//                                .withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white) : ContextCompat.getColor(this, R.color.text_color)),
//                        new PrimaryDrawerItem().withName(R.string.news).withIcon(R.mipmap.ic_news).withIdentifier(R.mipmap.ic_news)
//                                .withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white) : ContextCompat.getColor(this, R.color.text_color)),
//                        new PrimaryDrawerItem().withName(R.string.reading).withIcon(R.mipmap.ic_reading).withIdentifier(R.mipmap.ic_reading)
//                                .withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white) : ContextCompat.getColor(this, R.color.text_color)),
//                        new PrimaryDrawerItem().withName(R.string.collection).withIcon(R.mipmap.ic_collect_grey).withIdentifier(R.mipmap.ic_collect_grey)
//                                .withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white) : ContextCompat.getColor(this, R.color.text_color)),
//                        new SectionDrawerItem().withName(R.string.app_name).withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white) : ContextCompat.getColor(this, R.color.text_color)),
//                        new SecondaryDrawerItem().withName(Settings.isNightMode == true ? R.string.text_day_mode: R.string.text_night_mode)
//                                .withIcon(Settings.isNightMode == true ? R.mipmap.ic_day_white:R.mipmap.ic_night).withIdentifier(R.mipmap.ic_night)
//                                .withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white) : ContextCompat.getColor(this, R.color.text_light)),
//                        new SecondaryDrawerItem().withName(R.string.setting).withIcon(R.mipmap.ic_setting).withIdentifier(R.mipmap.ic_setting)
//                                .withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white):ContextCompat.getColor(this,R.color.text_light)),
//                        new SecondaryDrawerItem().withName(R.string.about).withIcon(R.mipmap.ic_about).withIdentifier(R.mipmap.ic_about)
//                                .withTextColor(Settings.isNightMode ? ContextCompat.getColor(this, R.color.white):ContextCompat.getColor(this,R.color.text_light))
//                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        switch (drawerItem.getIdentifier()) {
//                            case R.mipmap.ic_home:
//                                if (currentFragment instanceof DailyFragment) {
//                                    return false;
//                                }
//                                currentFragment = new DailyFragment();
//                                break;
//                            case R.mipmap.ic_reading:
//                                if (currentFragment instanceof BaseReadingFragment) {
//                                    return false;
//                                }
//                                new BaseNewsFragment();
//                                currentFragment = new BaseReadingFragment();
//                                break;
//                            case R.mipmap.ic_news:
//                                if (currentFragment instanceof BaseNewsFragment) {
//                                    return false;
//                                }
//                                currentFragment = new BaseNewsFragment();
//                                break;
//                            case R.mipmap.ic_science:
//                                if (currentFragment instanceof BaseScienceFragment) {
//                                    return false;
//                                }
//                                currentFragment = new BaseScienceFragment();
//                                break;
//                            case R.mipmap.ic_collect_grey:
//                                if (currentFragment instanceof BaseCollectionFragment) {
//                                    return false;
//                                }
//                                currentFragment = new BaseCollectionFragment();
//                                break;
//                            case R.mipmap.ic_night:
//                                Settings.isNightMode = !Settings.isNightMode;
//                                mSettings.putBoolean(mSettings.NIGHT_MODE, Settings.isNightMode);
//                                MainActivity.this.recreate();
//                                return false;
//                            case R.mipmap.ic_setting:
//                                Intent toSetting = new Intent(MainActivity.this, SettingsActivity.class);
//                                toSetting.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(toSetting);
//                                return false;
//                            case R.mipmap.ic_about:
//                                Intent toAbout = new Intent(MainActivity.this, AboutActivity.class);
//                                startActivity(toAbout);
//                                return false;
//                        }
//                        switchFragment();
//                        return false;
//                    }
//                })
//                .build();
    }


    //重力感应-摇晃手机操作
    @Override
    public void onSensorChanged(SensorEvent event) {

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
