package com.proyect.workgroup.domain;


import com.project.workgroup.model.MediaDataSource;
import com.project.workgroup.model.entidades.EventsWrapper;
import com.squareup.otto.Bus;

public class GetEventsUsecaseController implements GetEventsUsecase {


    private final MediaDataSource mMediaDataSource;
    private final Bus mUiBus;
    private int mCurrentPage = 1;

    public GetEventsUsecaseController(MediaDataSource mMediaDataSource, Bus mUiBus) {
        this.mMediaDataSource = mMediaDataSource;
        this.mUiBus = mUiBus;
        mUiBus.register(this);
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
    public void execute() {
        requestEvent();
        mCurrentPage++;
    }
}
