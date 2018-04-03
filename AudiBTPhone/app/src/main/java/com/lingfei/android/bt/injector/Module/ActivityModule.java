package com.lingfei.android.bt.injector.Module;

import android.app.Activity;

import com.lingfei.android.bt.injector.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sll on 2016/1/6.
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * @Provide: 在modules中，我们定义的方法是用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖。
     */
    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
