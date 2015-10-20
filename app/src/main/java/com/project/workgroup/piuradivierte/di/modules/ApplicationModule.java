package com.project.workgroup.piuradivierte.di.modules;

import android.content.Context;

import com.project.workgroup.piuradivierte.EventsApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Junior on 16/10/2015.
 */
@Module
public class ApplicationModule {
    private final EventsApp application;


    public ApplicationModule(EventsApp application) {
        this.application = application;
    }
    @Provides @Singleton
    Context providerApplicationContext(){
        return application;
    }
    public static class EventUsecaseMocule{

    }

}
