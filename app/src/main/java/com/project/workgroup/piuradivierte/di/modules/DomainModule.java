package com.project.workgroup.piuradivierte.di.modules;



import com.project.workgroup.model.rest.RestEventSource;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
/**
 * Created by Junior on 16/10/2015.
 */
@Module
public class DomainModule {

    @Provides @Singleton
    Bus provideBus () {
        return new Bus();
    }

    @Provides
    @Singleton RestEventSource provideDataSource (Bus bus) {
        return new RestEventSource(bus);
    }

}
