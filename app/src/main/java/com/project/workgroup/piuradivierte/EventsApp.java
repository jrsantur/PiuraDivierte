package com.project.workgroup.piuradivierte;

import android.app.Application;

import com.project.workgroup.piuradivierte.di.components.AppComponent;
import com.project.workgroup.piuradivierte.di.components.DaggerAppComponent;
import com.project.workgroup.piuradivierte.di.modules.ApplicationModule;
import com.project.workgroup.piuradivierte.di.modules.DomainModule;

/**
 * Created by Junior on 17/10/2015.
 */
public class EventsApp extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeDependencyInyector();
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
}
