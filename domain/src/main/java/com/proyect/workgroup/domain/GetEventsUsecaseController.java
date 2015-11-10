package com.proyect.workgroup.domain;


import com.project.workgroup.model.MediaDataSource;
import com.project.workgroup.model.entidades.EventsWrapper;
import com.squareup.otto.Bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class GetEventsUsecaseController implements GetEventsUsecase {

    private Logger logger = LoggerFactory.getLogger(GetEventsUsecaseController.class);
    private final MediaDataSource mMediaDataSource;
    private final Bus mUiBus;
    private int mCurrentPage = 1;

    @Inject
    public GetEventsUsecaseController(MediaDataSource mMediaDataSource, Bus mUiBus) {
        this.mMediaDataSource = mMediaDataSource;
        this.mUiBus = mUiBus;
        mUiBus.register(this);
        logger.error("se inyecto GetEventsUsecaseController");
    }


    @Override
    public void requestEvent() {
        mMediaDataSource.getEvents();
    }

    @Override
    public void sendEventToPresenter(EventsWrapper response) {
        mUiBus.post(response);
    }

    @Override
    public void unRegister() {
        mUiBus.unregister(this);
    }

    @Override
    public void execute() {
        requestEvent();
    }
}
