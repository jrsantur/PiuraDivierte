package com.project.workgroup.piuradivierte.mvp.views;

import com.project.workgroup.model.entidades.Event;

import java.util.List;

/**
 * Created by Junior on 16/10/2015.
 */
public interface EventsView extends MVPView {

    void showEvents(List<Event> eventList);

    void showLoading();

    void hideLoading();


    boolean isTheListEmpety();

    void appendMovies (List<Event> eventList);
}
