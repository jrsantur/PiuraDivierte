package com.project.workgroup.piuradivierte.di.components;

import com.project.workgroup.piuradivierte.di.modules.BasicEventsUsecaseModule;
import com.project.workgroup.piuradivierte.di.scope.PerActivity;
import com.project.workgroup.piuradivierte.view.activities.MainActivity;

import dagger.Component;

/**
 * Created by Junior on 17/10/2015.
 */


@PerActivity
@Component (dependencies = AppComponent.class, modules = BasicEventsUsecaseModule.class)
public interface BasicEventsUsecaseComponent {
    void inject (MainActivity mainActivity);
}
