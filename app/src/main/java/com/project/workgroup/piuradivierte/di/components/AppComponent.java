package com.project.workgroup.piuradivierte.di.components;

import com.project.workgroup.model.rest.RestEventSource;
import com.project.workgroup.piuradivierte.di.modules.ApplicationModule;
import com.project.workgroup.piuradivierte.di.modules.DomainModule;
import com.squareup.otto.Bus;

import javax.inject.Singleton;
import dagger.Component;
/**
 * Created by Junior on 16/10/2015.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        DomainModule.class,
})

public interface AppComponent {
    Bus bus();
    RestEventSource restMovieSource();
}
