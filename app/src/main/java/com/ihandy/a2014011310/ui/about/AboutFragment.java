

package com.ihandy.a2014011310.ui.about;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.support.CONSTANT;
import com.ihandy.a2014011310.support.HttpUtil;
import com.ihandy.a2014011310.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{

    private Preference mAppIntro;
    private Preference mEmail;
    private Preference mStudentID;
    private Preference mStudentClass;


    private final String APP_INTRO = "app_intro";
    private final String EMAIL = "email";
    private final String STUDENT_ID = "student_id";
    private final String STUDNET_CLASS = "student_class";


    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        mAppIntro = findPreference(APP_INTRO);
        mEmail = findPreference(EMAIL);
        mStudentID  =findPreference(STUDENT_ID);
        mStudentClass=findPreference(STUDNET_CLASS);

        mAppIntro.setOnPreferenceClickListener(this);
        mEmail.setOnPreferenceClickListener(this);
        mStudentID.setOnPreferenceClickListener(this);
        mStudentClass.setOnPreferenceClickListener(this);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressbar);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {

        if(mAppIntro == preference){
            Intent toIntro = new Intent(getActivity(),AppIntroActivity.class);
            startActivity(toIntro);
        }else if(mEmail == preference){
            Utils.copyToClipboard(getView(),getString(R.string.author_email));
        }else if (mStudentID == preference){
            Utils.copyToClipboard(getView(),getString(R.string.student_id));
        }else if (mStudentClass == preference){
            Utils.copyToClipboard(getView(),getString(R.string.student_class));
        }
        return false;
    }

    private Handler handle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            progressBar.setVisibility(View.GONE);
            return false;
        }
    });
}
