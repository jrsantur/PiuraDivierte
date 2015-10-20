package com.proyect.workgroup.domain;

import com.project.workgroup.model.entidades.EventDetail;

/**
 * Created by Junior on 17/10/2015.
 */
public interface GetEventDetailUsecase  extends Usecase{

    void requesEventDedtail(String eventId);

    void onEventDetailResponse(EventDetail response);

    void sendDetailEventToPresenter(EventDetail response);
}
