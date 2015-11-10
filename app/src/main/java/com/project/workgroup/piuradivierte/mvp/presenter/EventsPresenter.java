package com.project.workgroup.piuradivierte.mvp.presenter;

import android.util.Log;

import com.project.workgroup.model.entidades.EventsWrapper;
import com.project.workgroup.piuradivierte.mvp.views.EventsView;
import com.proyect.workgroup.domain.ConfigurationUsecase;
import com.proyect.workgroup.domain.GetEventsUsecase;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * Created by Junior on 16/10/2015.
 */
public class EventsPresenter extends Presenter{

    private final Bus mBus;
    private GetEventsUsecase mGetEventsUsecase;
    private EventsView mEventsView;
    private boolean isLoading = false;
    private Boolean mRegistered;

    private static final  String  TAG = EventsPresenter.class.getName();
    @Inject
    public EventsPresenter(GetEventsUsecase mGetEventsUsecase, Bus mBus) {
        this.mBus = mBus;
        this.mGetEventsUsecase = mGetEventsUsecase;
    }

    public void attachView(EventsView eventsView){
        mEventsView = eventsView;
    }

    @Subscribe
    public void onPopularEventsRecivrd(EventsWrapper eventsWrapper){
        if(mEventsView.isTheListEmpety()){
            mEventsView.hideLoading();
            mEventsView.showEvents(eventsWrapper.getResults());
        }
        else{
            //mEventsView.hideActionLabel();
            mEventsView.appendMovies(eventsWrapper.getResults());
        }
        isLoading = false;
    }


    public void onEndListReached(){
        mGetEventsUsecase.execute();
        //mEventsView.showLoadingLabel();
        isLoading = true;
    }

    @Override
    public void start() {
        if(mEventsView.isTheListEmpety()){
            mBus.register(this);
            mRegistered = true;
        }

        Log.e(TAG, "start presenter");

    }

    @Override
    public void stop() {
        mBus.unregister(this);
    }

    public boolean isLoading() {

        return isLoading;
    }

    public void setLoading(boolean isLoading) {

        this.isLoading = isLoading;
    }
}
