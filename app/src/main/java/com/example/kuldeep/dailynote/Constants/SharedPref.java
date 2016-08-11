package com.example.kuldeep.dailynote.Constants;

/**
 * Created by Kuldeep on 1/8/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    public SharedPref(Context mContext) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mSharedPreferences.edit();
    }


    public void logOut() {
        mEditor.putBoolean("IsLogin", false).commit();
        mEditor.putString("UserName", "").commit();
        mEditor.putString("UserEmail", "").commit();

    }

    //is login set
    public void putIsLogin(boolean IsLogin) {
        mEditor.putBoolean("IsLogin", IsLogin);
        mEditor.commit();
    }

    public boolean IsLogin() {
        return mSharedPreferences.getBoolean("IsLogin", false);
    }


    //set user name
    public void putUserName(String UserName) {
        mEditor.putString("UserName", UserName);
        mEditor.commit();
    }

    public String getUserName() {
        return mSharedPreferences.getString("UserName", "");
    }



    //set user email id
    public void putUserEmail(String UserEmail) {
        mEditor.putString("UserEmail", UserEmail);
        mEditor.commit();
    }

    public String getUserEmail() {
        return mSharedPreferences.getString("UserEmail", "");
    }


}
