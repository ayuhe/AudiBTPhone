package com.lingfei.android.uilib.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingfei.android.uilib.R;
import com.lingfei.android.uilib.base.inteface.HasComponent;
import com.lingfei.android.uilib.util.ResourceUtils;
import com.lingfei.android.uilib.widget.ProgressBarCircularIndeterminate;

import java.lang.reflect.Field;

/**
	* BaseFragment通常的Fragment可以继承其
	*/
public abstract class BaseSupportFragment extends ProgressSupportFragment{

				private ImageView ivIcon;
				private TextView tvError, tvEmpty, tvLoading;
				private Button btnReload;

				public abstract void initInjector();

				public abstract int initContentView();

				/**
					* 得到Activity传进来的值
					*/
				public abstract void getBundle(Bundle bundle);

				/**
					* 初始化控件
					*/
				public abstract void initUI(View view);

				/**
					* 在监听器之前把数据准备好
					*/
				public abstract void initData();

				@Override
				public void onCreate(Bundle savedInstanceState){
								super.onCreate(savedInstanceState);
				}

				@Override
				public void onViewCreated(View view, Bundle savedInstanceState){
								initInjector();
								getBundle(getArguments());
								initUI(view);
								initData();
								super.onViewCreated(view, savedInstanceState);
				}

				@Override
				public View onCreateContentView(LayoutInflater inflater){
								return inflater.inflate(initContentView(), null);
				}

				@Override
				public View onCreateContentErrorView(LayoutInflater inflater){
								View error = inflater.inflate(R.layout.base_frag_error_view_layout, null);
								ivIcon = (ImageView) error.findViewById(R.id.iv_icon);
								tvError = (TextView) error.findViewById(R.id.tvError);
								error.findViewById(R.id.btnReload).setOnClickListener(new View.OnClickListener(){
												@Override
												public void onClick(View view){
																onReloadClicked();
												}
								});
								return error;
				}

				@Override
				public View onCreateContentEmptyView(LayoutInflater inflater){
								View empty = inflater.inflate(R.layout.base_frag_empty_view_layout, null);
								ivIcon = (ImageView) empty.findViewById(R.id.iv_icon);
								tvEmpty = (TextView) empty.findViewById(R.id.tvEmpty);
								btnReload = (Button) empty.findViewById(R.id.btnReload);
								tvEmpty.setOnClickListener(new View.OnClickListener(){
												@Override
												public void onClick(View view){
																onReloadClicked();
												}
								});
								btnReload.setOnClickListener(new View.OnClickListener(){
												@Override
												public void onClick(View view){
																onReloadClicked();
												}
								});
								return empty;
				}

				@Override
				public View onCreateProgressView(LayoutInflater inflater){
								View loading = inflater.inflate(R.layout.base_frag_loading_view_layout, null);
								tvLoading = (TextView) loading.findViewById(R.id.tvLoading);
								ProgressBarCircularIndeterminate progressBar =
																(ProgressBarCircularIndeterminate) loading.findViewById(R.id.progress_view);
								progressBar.setBackgroundColor(ResourceUtils.getThemeColor(getActivity()));
								return loading;
				}

				public void setIvIcon(int iconId){
								if(null != ivIcon){
												ivIcon.setImageResource(iconId);
								}
				}

				public void setErrorText(String text){
								tvError.setText(text);
				}

				public void setErrorText(int textResId){
								setErrorText(getString(textResId));
				}

				public void setEmptyText(String text){
								tvEmpty.setText(text);
				}

				public void setEmptyButtonVisible(int visible){
								btnReload.setVisibility(visible);
				}

				public void setEmptyText(int textResId){
								setEmptyText(getString(textResId));
				}

				public void setLoadingText(String text){
								tvLoading.setText(text);
				}

				public void setLoadingText(int textResId){
								setLoadingText(getString(textResId));
				}

				//Override this to reload
				public void onReloadClicked(){

				}

				public boolean onKeyDown(int keyCode, KeyEvent event){
								return false;
				}

				@SuppressWarnings("unchecked")
				protected <C> C getComponent(Class<C> componentType){
								return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
				}
				//==============================main override =====================

				@Override
				public void onDetach(){
								super.onDetach();
								try{
												Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
												childFragmentManager.setAccessible(true);
												childFragmentManager.set(this, null);
								}
								catch(NoSuchFieldException e){
												throw new RuntimeException(e);
								}
								catch(IllegalAccessException e){
												throw new RuntimeException(e);
								}
				}
}
