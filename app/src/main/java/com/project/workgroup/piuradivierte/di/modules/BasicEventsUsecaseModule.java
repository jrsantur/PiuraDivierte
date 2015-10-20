package com.project.workgroup.piuradivierte.di.modules;

import com.project.workgroup.model.rest.RestEventSource;
import com.proyect.workgroup.domain.GetEventsUsecase;
import com.proyect.workgroup.domain.GetEventsUsecaseController;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
/**
 * Created by Junior on 17/10/2015.
 */

@Module
public class BasicEventsUsecaseModule {

    @Provides
    GetEventsUsecase provideMoviesUsecase (Bus bus, RestEventSource eventSource) {
        return new GetEventsUsecaseController(eventSource, bus);
    }
}
