package com.project.workgroup.model.rest;


import com.project.workgroup.model.entidades.EventDetail;
import com.project.workgroup.model.entidades.EventsWrapper;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Junior on 09/10/2015.
 */
public interface EventDatabaseAPI {
    @GET("/event")
    void getPopularEvents(
            @Query("") String apiKey,
            Callback<EventsWrapper> callback);

    @GET("/event/{id}")
    void getEventDetail(
            @Query("api_key") String apiKey, @Path("id") String id,
            Callback<EventDetail> callback
    );

}
