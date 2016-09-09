

package com.ihandy.a2014011310.ui.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.CONSTANT;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.support.Utils;



public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
    Preference.OnPreferenceClickListener{
    private Settings mSettings;


    private Preference mLanguage;
    private Preference mSwipeBack;
    private CheckBoxPreference mAutoRefresh;
    private CheckBoxPreference mNightMode;
    private CheckBoxPreference mShakeToReturn;
    private CheckBoxPreference mNoPicMode;
    private CheckBoxPreference mExitConfirm;
    private Preference mClearCache ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        mSettings = Settings.getInstance();

        mLanguage = findPreference(Settings.LANGUAGE);
        mSwipeBack = findPreference(Settings.SWIPE_BACK);

        mAutoRefresh = (CheckBoxPreference) findPreference(Settings.AUTO_REFRESH);
        mNightMode = (CheckBoxPreference) findPreference(Settings.NIGHT_MODE);
        mShakeToReturn = (CheckBoxPreference) findPreference(Settings.SHAKE_TO_RETURN);
        mNoPicMode = (CheckBoxPreference) findPreference(Settings.NO_PIC_MODE);
        mExitConfirm = (CheckBoxPreference) findPreference(Settings.EXIT_CONFIRM);
        mClearCache = findPreference(Settings.CLEAR_CACHE);

        mLanguage.setSummary(this.getResources().getStringArray(R.array.langs)[Utils.getCurrentLanguage()]);
        mSwipeBack.setSummary(this.getResources().getStringArray(R.array.swipe_back)[Settings.swipeID]);

        mAutoRefresh.setChecked(Settings.isAutoRefresh);
        mNightMode.setChecked(Settings.isNightMode);
        mShakeToReturn.setChecked(Settings.isShakeMode);
        mExitConfirm.setChecked(Settings.isExitConfirm);
        mNoPicMode.setChecked(Settings.noPicMode);

        mAutoRefresh.setOnPreferenceChangeListener(this);
        mNightMode.setOnPreferenceChangeListener(this);
        mShakeToReturn.setOnPreferenceChangeListener(this);
        mExitConfirm.setOnPreferenceChangeListener(this);
        mNoPicMode.setOnPreferenceChangeListener(this);


        mLanguage.setOnPreferenceClickListener(this);
        mSwipeBack.setOnPreferenceClickListener(this);
        mClearCache.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference == mAutoRefresh){
            Settings.isAutoRefresh = Boolean.valueOf(newValue.toString());
            mSettings.putBoolean(mSettings.AUTO_REFRESH, Settings.isAutoRefresh);
            return true;
        }else if(preference == mNightMode){
            Settings.isNightMode = Boolean.valueOf(newValue.toString());
            Settings.needRecreate = true;
            mSettings.putBoolean(mSettings.NIGHT_MODE, Settings.isNightMode);

            if(mSettings.isNightMode && Utils.getSysScreenBrightness() > CONSTANT.NIGHT_BRIGHTNESS){
                Utils.setSysScreenBrightness(CONSTANT.NIGHT_BRIGHTNESS);
            }else if(mSettings.isNightMode == false && Utils.getSysScreenBrightness() == CONSTANT.NIGHT_BRIGHTNESS){
                Utils.setSysScreenBrightness(CONSTANT.DAY_BRIGHTNESS);
            }
            getActivity().recreate();
            return true;
        }else if(preference == mShakeToReturn){
            Settings.isShakeMode = Boolean.valueOf(newValue.toString());
            mSettings.putBoolean(mSettings.SHAKE_TO_RETURN,mSettings.isShakeMode);
            return true;
        }else if(preference == mExitConfirm){
            Settings.isExitConfirm = Boolean.valueOf(newValue.toString());
            mSettings.putBoolean(mSettings.EXIT_CONFIRM, Settings.isExitConfirm);
            return true;
        }else if(preference == mNoPicMode){
            Settings.noPicMode = Boolean.valueOf(newValue.toString());
            Settings.needRecreate = true;
            mSettings.putBoolean(mSettings.NO_PIC_MODE, Settings.noPicMode);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference == mLanguage){
            showLangDialog();
        }else if(preference == mClearCache){
            Utils.clearCache();
            Settings.needRecreate = true;
            Snackbar.make(getView(), R.string.text_clear_cache_successful,Snackbar.LENGTH_SHORT).show();
        }else if(preference == mSwipeBack){
            showSwipeSettingsDialog();
        }
        return false;
    }

    private void showLangDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.text_language))
                .setSingleChoiceItems(
                        getResources().getStringArray(R.array.langs), Utils.getCurrentLanguage(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which != Utils.getCurrentLanguage()) {
                                    mSettings.putInt(Settings.LANGUAGE, which);
                                    Settings.needRecreate = true;
                                }
                                dialog.dismiss();
                                if (Settings.needRecreate) {
                                    getActivity().recreate();
                                }
                            }
                        }
                ).show();

    }

    private void showSwipeSettingsDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.text_swipe_to_return))
                .setSingleChoiceItems(
                        getResources().getStringArray(R.array.swipe_back), Settings.swipeID,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("SearchID",Settings.swipeID+"");
                                Log.d("which",which+"");
                                dialog.dismiss();
                                if(which != Settings.swipeID){
                                    Settings.swipeID = which;
                                    mSettings.putInt(Settings.SWIPE_BACK,which);
                                    getActivity().recreate();
                                }
                            }
                        }

                ).show();
    }
}
