package com.project.workgroup.piuradivierte.mvp.presenter;

import com.project.workgroup.model.entidades.EventDetail;
import com.project.workgroup.piuradivierte.mvp.views.EventDetailView;
import com.proyect.workgroup.domain.GetEventDetailUsecase;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * Created by TALLER on 21/10/2015.
 */
public class EventDetailPresenter  extends Presenter {

    private final Bus mBus;
    private EventDetailView mEventDetailView;
    private final GetEventDetailUsecase mEventDetailUsecase;

    @Inject
    public EventDetailPresenter(GetEventDetailUsecase mEventDetailUsecase , Bus mBus) {

        this.mEventDetailUsecase = mEventDetailUsecase;
        this.mBus = mBus;
    }
    public  void attachView( EventDetailView mEventDetailView){
        this.mEventDetailView = mEventDetailView;
        //this.mEventDetailView.showFilmCover(MoviesActivity.sPhotoCache.get(0));
        mEventDetailUsecase.execute();
    }

    public void showTitle(String title){
        mEventDetailView.setTitle(title);
    }

    public void showDescription(String description){
        mEventDetailView.setDescription(description);
    }


    @Override
    public void start() {
        mBus.register(this);
    }

    @Override
    public void stop() {
        mBus.unregister(this);
    }

    @Subscribe
    public void onDetailInformationRecived(EventDetail response){
        showTitle(response.getTitle());
        showDescription(response.getDescripcion_lugar());

    }

}
