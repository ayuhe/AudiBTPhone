package com.lingfei.android.bt.ui.btphone;

import com.lingfei.android.bt.injector.Module.ActivityModule;
import com.lingfei.android.bt.injector.PerActivity;
import com.lingfei.android.bt.injector.component.ApplicationComponent;
import com.lingfei.android.bt.ui.btphone.contact.ContactFragment;
import com.lingfei.android.bt.ui.btphone.dail.DailFragment;
import com.lingfei.android.bt.ui.btphone.device.DeviceFragment;

import dagger.Component;

/**
	* BTPhoneMainComponent
	*
	* @author heyu
	* @date 2017/6/15.
	*/
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface BTPhoneMainComponent{
				void inject(BTPhoneMainActivity activity);
				void inject(DailFragment fragment);
				void inject(ContactFragment fragment);
				void inject(DeviceFragment fragment);
}
