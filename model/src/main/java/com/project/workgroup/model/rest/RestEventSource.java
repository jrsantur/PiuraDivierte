package com.project.workgroup.model.rest;

import com.project.workgroup.common.Constants;
import com.project.workgroup.model.entidades.EventDetail;
import com.project.workgroup.model.entidades.EventsWrapper;
import com.squareup.otto.Bus;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Junior on 09/10/2015.
 */
public class RestEventSource implements RestDataSources {


    private final EventDatabaseAPI eventDBApi;
    private final Bus bus;


    public RestEventSource(Bus bus){

        RestAdapter movieAPIRest = new RestAdapter.Builder()
                .setEndpoint(Constants.EVENTS_DB_HOST)
                .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .build();

        eventDBApi = movieAPIRest.create(EventDatabaseAPI.class);
        this.bus = bus;

    }


    @Override
    public void getEvents() {
        eventDBApi.getPopularEvents(null, retrofitCallback);
    }

    @Override
    public void getDetailEvent(String id) {
        eventDBApi.getEventDetail(null,id,  retrofitCallback);
    }


    public Callback retrofitCallback = new Callback() {


        @Override
        public void success(Object o, Response response) {


            if(o instanceof EventDetail){
                EventDetail detailResponse = (EventDetail)o ;
                bus.post(detailResponse);
            }

            if (o instanceof EventsWrapper){
                EventsWrapper eventsApiResponse = (EventsWrapper) o ;
                bus.post(eventsApiResponse);
            }


        }

        @Override
        public void failure(RetrofitError error) {
            System.out.println("[DEBUG] RestMovieSource failure - " + error.getMessage());
        }
    };


    @Override
    public void getEventsByPage(int page) {

    }


}
