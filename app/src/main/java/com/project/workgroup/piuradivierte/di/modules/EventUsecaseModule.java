package com.project.workgroup.piuradivierte.di.modules;

/**
 * Created by Junior on 17/10/2015.
 */

import com.project.workgroup.model.rest.RestEventSource;
import com.proyect.workgroup.domain.GetEventDetailUsecase;
import com.proyect.workgroup.domain.GetEventDetailUsecaseController;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module
public class EventUsecaseModule {


    private final String eventId;

    public EventUsecaseModule(String eventId) {
        this.eventId = eventId;
    }


    @Provides
    GetEventDetailUsecase provideGetEventDetailUsecase (Bus bus, RestEventSource eventSource) {
        return new GetEventDetailUsecaseController(eventId, bus, eventSource);
    }


}
