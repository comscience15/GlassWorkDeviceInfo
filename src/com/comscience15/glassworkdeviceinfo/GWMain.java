package com.comscience15.glassworkdeviceinfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.comscience15.glassworkdeviceinfo.ApkInfo;
import com.comscience15.glassworkdeviceinfo.adapter.*;
import com.comscience15.glassworkdeviceinfo.app.*;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.PixelFormat;
import android.text.method.ArrowKeyMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GWMain extends Activity {
//	private Movie mMovie;
//	private long mMovieStart;
//	private static final boolean DECODE_STREAM = true;
//	private static byte[] streamToBytes(InputStream is){
//		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
//		byte[] buffer = new byte[1024];
//		int len;
//		try{
//			while ((len = is.read(buffer)) >= 0){
//				os.write(buffer, 0, len);
//			}
//		}catch (java.io.IOException e){
//			
//		}
//		return os.toByteArray();
//	}
//	
//	public AnimationView(Context context, AttributeSet attrs){
//		super(context, attrs);
//		setFocusable(true);
//		java.io.InputStream is;
//		is = context.getResources().openRawResource(R.drawable.android1);
//		if(DECODE_STREAM){
//			mMovie = Movie.decodeStream(is);
//		}else{
//			byte[] array = streamToBytes(is);
//			mMovie = Movie.decodeByteArray(array, 0, array.length);
//		}
//	}
//	
//	@Override
//	public void onDraw(Canvas canvas){
//		long now = android.os.SystemClock.uptimeMillis();
//		if(mMovieStart == 0){
//			mMovieStart = now;
//		}
//		if(mMovie != null){
//			int dur = mMovie.duration();
//			if(dur == 0){
//				dur = 300;
//			}
//			int relTime = (int)((now - mMovieStart)%dur);
//			Log.d("","real time :: " + relTime);
//			mMovie.setTime(relTime);
//			mMovie.draw(canvas, getWidth() - 200, getHeight() - 200);
//			invalidate();
//		}
//	}
	
////	public void onAttachedToWindow(){
////		super.onAttachedToWindow();
////		Window window = getWindow();
////		window.setFormat(PixelFormat.RGBA_8888);
////	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
//		StartAnimations();
		
		Thread thread = new Thread(){
			
			public void run(){
				try {
					sleep(2000);
				} catch (InterruptedException e){
					e.printStackTrace();
				} finally{
					startActivity(new Intent(GWMain.this,SplashScreen.class));
					//finish splash screen and won't show up again if hit back (hard) button
					finish();
				}
			}
		};
		thread.start();
	}
	
////	private void StartAnimations() {
////		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
////		anim.reset();
////		LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
////		l.clearAnimation();
////		l.startAnimation(anim);
////		
////		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
////		anim.reset();
////		ImageView iv = (ImageView) findViewById(R.id.logo);
////		iv.clearAnimation();
////		iv.startAnimation(anim);
////	}
}
