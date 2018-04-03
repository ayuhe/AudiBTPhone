package com.lingfei.android.bt.ui.btmusic;


import com.lingfei.android.bt.injector.Module.ActivityModule;
import com.lingfei.android.bt.injector.PerActivity;
import com.lingfei.android.bt.injector.component.ApplicationComponent;

import dagger.Component;

/**
 * Created by heyu on 2016/7/26.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface BTMusicComponent{
    void inject(BTMusicActivity activity);
}
