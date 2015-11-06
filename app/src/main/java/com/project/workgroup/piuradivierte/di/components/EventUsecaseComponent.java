package com.project.workgroup.piuradivierte.di.components;

import com.project.workgroup.piuradivierte.di.modules.EventUsecaseModule;
import com.project.workgroup.piuradivierte.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by Junior on 05/11/2015.
 */

@PerActivity@Component(dependencies = AppComponent.class, modules = EventUsecaseModule.class)
public interface EventUsecaseComponent {
}
