package com.proyect.workgroup.domain;

import com.project.workgroup.model.entidades.EventsWrapper;

/**
 * Created by Junior on 16/10/2015.
 */
public interface GetEventsUsecase extends Usecase{

    void requestEvent();
    void sendEventToPresenter(EventsWrapper response);
    void unRegister ();
}
