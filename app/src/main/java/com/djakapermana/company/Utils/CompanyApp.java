package com.djakapermana.company.Utils;

import android.support.multidex.MultiDex;

import com.orm.SugarApp;

public class CompanyApp extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
