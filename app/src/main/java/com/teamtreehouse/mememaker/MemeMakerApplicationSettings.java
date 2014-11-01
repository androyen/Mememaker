package com.teamtreehouse.mememaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.teamtreehouse.mememaker.utils.StorageType;

/**
 * Created by Evan Anger on 8/13/14.
 */
public class MemeMakerApplicationSettings {

    //Handle shared preferences
    SharedPreferences mSharedPreferences;

    public MemeMakerApplicationSettings(Context context) {

            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    //get and set sharedpreferences
    public String getStoragePreference() {

        //default to internal storage
        return mSharedPreferences.getString("Storage", StorageType.INTERNAL);
    }

    public void setSharedPreference(String storageType) {
        mSharedPreferences
                .edit()
                .putString("Storage", storageType)
                .apply(); //Asynchronous on another worker thread
    }
}
