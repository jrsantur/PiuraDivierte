package com.project.workgroup.model.rest;

import com.project.workgroup.model.MediaDataSource;

/**
 * Created by Junior on 09/10/2015.
 */
public interface RestDataSources extends MediaDataSource {

    public void getEventsByPage(int page);
}
