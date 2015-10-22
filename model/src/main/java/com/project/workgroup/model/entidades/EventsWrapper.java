package com.project.workgroup.model.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Junior on 09/10/2015.
 */
public class EventsWrapper implements Serializable{

    private List<Event> results;
    private Number total_result;


    public EventsWrapper(List<Event> results){
        this.results = results;
    }

    public List<Event> getResults() {
        return results;
    }

    public void setResults(List<Event> results) {
        this.results = results;
    }

    public Number getTotal_result() {
        return total_result;
    }

    public void setTotal_result(Number total_result) {
        this.total_result = total_result;
    }

}
