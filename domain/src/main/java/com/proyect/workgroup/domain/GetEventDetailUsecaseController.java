package com.proyect.workgroup.domain;

import com.project.workgroup.model.MediaDataSource;
import com.project.workgroup.model.entidades.EventDetail;
import com.squareup.otto.Bus;

/**
 * Created by Junior on 17/10/2015.
 */
public class GetEventDetailUsecaseController implements GetEventDetailUsecase {

    private final MediaDataSource mMediaDataSource;
    private final String mEventId;
    private final Bus mUiBus;
    private EventDetail mEventDetail;


    public GetEventDetailUsecaseController(String mEventId, Bus mUiBus, MediaDataSource dataSource) {

        this.mUiBus = mUiBus;
        this.mEventId = mEventId;
        this.mMediaDataSource = dataSource;
        mUiBus.register(this);
    }


    @Override
    public void requesEventDedtail(String eventId) {

    }

    @Override
    public void onEventDetailResponse(EventDetail response) {

    }

    @Override
    public void sendDetailEventToPresenter(EventDetail response) {

    }

    @Override
    public void execute() {
        requesEventDedtail(mEventId);
    }
}
