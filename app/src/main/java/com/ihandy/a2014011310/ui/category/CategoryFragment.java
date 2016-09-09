

package com.ihandy.a2014011310.ui.category;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.api.ScienceApi;
import com.ihandy.a2014011310.support.CONSTANT;
import com.ihandy.a2014011310.support.Utils;
import com.ihandy.a2014011310.ui.MainActivity;


public class CategoryFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener{

    private CheckBoxPreference mEntertainment;
    private CheckBoxPreference mHealth;
    private CheckBoxPreference mNigeria;
    private CheckBoxPreference mSports;
    private CheckBoxPreference mTechnology;
    private CheckBoxPreference mTopStories;
    private CheckBoxPreference mWorld;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("aaa","onCreated");
        addPreferencesFromResource(R.xml.category);

        mEntertainment = (CheckBoxPreference) findPreference(getString(R.string.id_category_entertainment));
        mHealth = (CheckBoxPreference) findPreference(getString(R.string.id_category_health));
        mNigeria = (CheckBoxPreference) findPreference(getString(R.string.id_category_national));
        mSports = (CheckBoxPreference) findPreference(getString(R.string.id_category_sports));
        mTechnology = (CheckBoxPreference) findPreference(getString(R.string.id_category_technology));
        mTopStories = (CheckBoxPreference) findPreference(getString(R.string.id_category_top_stories));
        mWorld = (CheckBoxPreference) findPreference(getString(R.string.id_category_world));

        mEntertainment.setChecked(!ScienceApi.rule_out[0]);
        mHealth.setChecked(!ScienceApi.rule_out[1]);
        mNigeria.setChecked(!ScienceApi.rule_out[2]);
        mSports.setChecked(!ScienceApi.rule_out[3]);
        mTechnology.setChecked(!ScienceApi.rule_out[4]);
        mTopStories.setChecked(!ScienceApi.rule_out[5]);
        mWorld.setChecked(!ScienceApi.rule_out[6]);

        mEntertainment.setOnPreferenceChangeListener(this);
        mHealth.setOnPreferenceChangeListener(this);
        mNigeria.setOnPreferenceChangeListener(this);
        mSports.setOnPreferenceChangeListener(this);
        mTechnology.setOnPreferenceChangeListener(this);
        mTopStories.setOnPreferenceChangeListener(this);
        mWorld.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.w("aaa","change");
        if(preference == mEntertainment){
            ScienceApi.rule_out[0] = !Boolean.valueOf(newValue.toString());
            ScienceApi.needUpdate=true;
            return true;
        }else if(preference == mHealth){
            ScienceApi.rule_out[1] = !Boolean.valueOf(newValue.toString());
            ScienceApi.needUpdate=true;
            return true;
        }else if(preference == mNigeria){
            ScienceApi.rule_out[2] = !Boolean.valueOf(newValue.toString());
            ScienceApi.needUpdate=true;
            return true;
        }else if(preference == mSports){
            ScienceApi.rule_out[3] = !Boolean.valueOf(newValue.toString());
            ScienceApi.needUpdate=true;
            return true;
        }else if(preference == mTechnology){
            ScienceApi.rule_out[4] = !Boolean.valueOf(newValue.toString());
            ScienceApi.needUpdate=true;
            return true;
        }else if(preference == mTopStories){
            ScienceApi.rule_out[5] = !Boolean.valueOf(newValue.toString());
            ScienceApi.needUpdate=true;
            return true;
        }else if(preference == mWorld){
            ScienceApi.rule_out[6] = !Boolean.valueOf(newValue.toString());
            ScienceApi.needUpdate=true;
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Log.w("aaa","click");
        return false;
    }


}
