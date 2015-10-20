package com.project.workgroup.model.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Junior on 09/10/2015.
 */
public class EventsWrapper implements Serializable{

    private List<Event> results;

    public EventsWrapper(List<Event> results){
        this.results = results;
    }

    public List<Event> getResults() {
        return results;
    }

    public void setResults(List<Event> results) {
        this.results = results;
    }
}
