package com.project.workgroup.piuradivierte;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.project.workgroup.piuradivierte.di.components.AppComponent;
import com.project.workgroup.piuradivierte.di.components.DaggerAppComponent;
import com.project.workgroup.piuradivierte.di.modules.ApplicationModule;
import com.project.workgroup.piuradivierte.di.modules.DomainModule;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Junior on 17/10/2015.
 */
public class EventsApp extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeDependencyInyector();
        this.printHasKey();
    }
    private void initializeDependencyInyector(){
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .domainModule(new DomainModule())
                .build();
    }

    public AppComponent getAppComponent() {

        return mAppComponent;
    }

    private void printHasKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    " com.project.workgroup.piuradivierte",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("PROJECT:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
}
