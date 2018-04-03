package com.lingfei.android.bt.ui.btphone.device;

import android.support.annotation.NonNull;

import com.lingfei.android.bt.injector.PerActivity;
import com.lingfei.android.bt.ui.btphone.util.BTControler;

import javax.inject.Inject;

/**
	* DevicePresenter
	*
	* @author heyu
	* @date 2017/6/20.
	*/
@PerActivity
public class DevicePresenter implements DeviceContract.Presenter{
				DeviceContract.View mView;

				@Inject
				public DevicePresenter(){
				}

				@Override
				public void initData(){
								BTControler.getInstance().openPairList();
								BTControler.getInstance().getCurrentBTDeviceLocalName();
								BTControler.getInstance().getCurrentBTDeviceLocalPassword();
								BTControler.getInstance().getCurConnectedBtAddr();
				}

				@Override
				public void attachView(@NonNull DeviceContract.View view){
								this.mView = view;

				}

				@Override
				public void detachView(){
								this.mView = null;
				}
}
