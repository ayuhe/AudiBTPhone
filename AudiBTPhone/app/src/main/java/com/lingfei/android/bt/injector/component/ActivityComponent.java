package com.lingfei.android.bt.injector.component;

import android.app.Activity;


import com.lingfei.android.bt.injector.Module.ActivityModule;
import com.lingfei.android.bt.injector.PerActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();
}
