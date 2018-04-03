package com.lingfei.android.uilib.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;

import java.io.InputStream;

/**
	* Created by Administrator on 2016/9/13.
	*/
public class BitmapUtil{
				public static Bitmap readBitMap(Context context, int resId){
								BitmapFactory.Options opt = new BitmapFactory.Options();
								opt.inPreferredConfig = Bitmap.Config.RGB_565;
								opt.inPurgeable = true;
								opt.inInputShareable = true;
								try{
												//获取资源图片
												InputStream is = context.getResources().openRawResource(resId);
												return BitmapFactory.decodeStream(is, null, opt);
								}
								catch(Resources.NotFoundException ex){
												ex.printStackTrace();
								}

								return null;
				}

				public static Bitmap createReflectedImage(Bitmap originalImage){
								if(null == originalImage){
												return originalImage;
								}
								int width = originalImage.getWidth();
								int height = originalImage.getHeight();
								Matrix matrix = new Matrix();
								// 实现图片翻转90度
								matrix.preScale(1, -1);

								// 创建倒影图片（是原始图片的一半大小）
								Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);

								// 创建总图片（原图片 + 倒影图片）
								Bitmap finalReflection = Bitmap.createBitmap(width, (height + height / 3 + 10), Bitmap.Config.RGB_565);

								// 创建画布
								Canvas canvas = new Canvas(finalReflection);
								canvas.drawBitmap(originalImage, 0, 0, null);

								//把倒影图片画到画布上
								canvas.drawBitmap(reflectionImage, 0, height + 8, null);
								Paint shaderPaint = new Paint();

								//创建线性渐变LinearGradient对象
								LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, finalReflection.getHeight() + 1,
								                                           new int[]{0x55000000, 0x77000000, 0x99000000, 0xBB000000, 0xDD000000, 0xFF000000},
								                                           new float[]{0, 0.2f, 0.4f, 0.6f, 0.8f, 1f},
								                                           Shader.TileMode.CLAMP);
								shaderPaint.setShader(shader);
								//画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
								canvas.drawRect(0, height + 8, width, finalReflection.getHeight(), shaderPaint);
								return finalReflection;
				}

				public static Bitmap createReflectedImage2(Bitmap originalImage){
								if(null == originalImage){
												return originalImage;
								}
								int width = originalImage.getWidth();
								int height = originalImage.getHeight();
								Matrix matrix = new Matrix();
								// 实现图片翻转90度
								matrix.preScale(1, -1);

								// 创建倒影图片（是原始图片的一半大小）
								Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);

								// 创建总图片（原图片 + 倒影图片）
								Bitmap finalReflection = Bitmap.createBitmap(width, (height + height / 3), Bitmap.Config.RGB_565);

								// 创建画布
								Canvas canvas = new Canvas(finalReflection);
								canvas.drawBitmap(originalImage, 0, 0, null);

								//把倒影图片画到画布上
								canvas.drawBitmap(reflectionImage, 0, height + 10, null);
								Paint shaderPaint = new Paint();

								//创建线性渐变LinearGradient对象
								LinearGradient shader = new LinearGradient(0, height, 0, finalReflection.getHeight() + 1,
								                                           new int[]{0x55000000, 0x77000000, 0x99000000, 0xBB000000, 0xDD000000, 0xFF000000},
								                                           new float[]{0, 0.2f, 0.4f, 0.6f, 0.8f, 1f},
								                                           Shader.TileMode.CLAMP);
								shaderPaint.setShader(shader);
								//画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
								canvas.drawRect(0, height + 10, width, finalReflection.getHeight(), shaderPaint);
								originalImage.recycle();
								reflectionImage.recycle();

								return finalReflection;
				}
}
