package com.comscience15.glassworkdeviceinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.comscience15.glassworkdeviceinfo.ApkInfo;
import com.comscience15.glassworkdeviceinfo.adapter.*;
import com.comscience15.glassworkdeviceinfo.app.*;
import com.comscience15.glassworkdeviceinfo.activity.ApkListActivity;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.method.ArrowKeyMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class GWMain extends Activity implements OnItemClickListener{

	private TextView DVBrand, DVModel, DVAndroidOS, DVPgkGames, DVLogcat, DVBoard, DVBootLoader,DVCPUabi,DVCPUabi2, DVDisplay, DVGL_Renderer, DVGL_Vendor,DVGL_Version, DVGL_Extensions, DVSerial, 
		DVBrand_edt,DVModel_edt, DVAndroidOS_edt, DVPgkGames_edt, DVLogcat_edt, DVBoard_edt, DVBootLoader_edt, DVCPUabi_edt, DVCPUabi2_edt, DVDisplay_edt, DVGL_Renderer_edt, DVGL_Vendor_edt, DVGL_Version_edt, DVGL_Extensions_edt, DVSerial_edt;
	private GLSurfaceView glSurfaceView;
	private String glVendor1, glRenderer1, glVersion1;
	private RelativeLayout rlLayout;
    PackageManager packageManager;
    ListView apkList;
    
    private GLSurfaceView.Renderer glVersion = new Renderer() {
	    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	    	Log.d("GPU info", "gl renderer: "+gl.glGetString(GL10.GL_RENDERER));
	    	Log.d("GPU info", "gl vendor: "+gl.glGetString(GL10.GL_VENDOR));
	        Log.d("GPU info", "gl version: "+gl.glGetString(GL10.GL_VERSION));
	        Log.d("GPU info", "gl extensions: "+gl.glGetString(GL10.GL_EXTENSIONS));
	        
	        glRenderer1 = gl.glGetString(GL10.GL_RENDERER);
	        glVendor1 = gl.glGetString(GL10.GL_VENDOR);
	        glVersion1 = gl.glGetString(GL10.GL_VERSION);
	        runOnUiThread(new Runnable() {
	
	            @Override
	            public void run() {
	            	DVGL_Version_edt.setText(glVersion1);
	            	DVGL_Vendor_edt.setText(glVendor1);
	            	DVGL_Renderer_edt.setText(glRenderer1);
	            	rlLayout.removeView(glSurfaceView);
	
	            }
	        });
	     }
	    
	    @Override
	    public void onSurfaceChanged(GL10 gl, int width, int height) {
	        // TODO Auto-generated method stub

	    }

	    @Override
	    public void onDrawFrame(GL10 gl) {
	        // TODO Auto-generated method stub

	    }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gwmain);
		
		rlLayout = (RelativeLayout)findViewById(R.id.rlLayout);
	
		DVBrand = (TextView) findViewById(R.id.showBrand);
		DVBrand_edt = (TextView) findViewById(R.id.deviceBrand);
		DVBrand_edt.setTextColor(Color.GREEN);
		DVBrand_edt.setText((android.os.Build.MANUFACTURER).toUpperCase());
		
		DVModel = (TextView) findViewById(R.id.showModel);
		DVModel_edt = (TextView) findViewById(R.id.deviceModel);
		DVModel_edt.setTextColor(Color.GREEN);
		DVModel_edt.setText(android.os.Build.MODEL);
		
		DVAndroidOS = (TextView) findViewById(R.id.showOS);
		DVAndroidOS_edt = (TextView) findViewById(R.id.deviceOS);
		DVAndroidOS_edt.setTextColor(Color.GREEN);
		DVAndroidOS_edt.setText(android.os.Build.VERSION.RELEASE);
		
		DVBoard = (TextView) findViewById(R.id.showBoard);
		DVBoard_edt = (TextView) findViewById(R.id.deviceBoard);
		DVBoard_edt.setTextColor(Color.GREEN);
		DVBoard_edt.setText(android.os.Build.BOARD);
		
		DVBootLoader = (TextView) findViewById(R.id.showBootLoader);
		DVBootLoader_edt = (TextView) findViewById(R.id.deviceBootLoader);
		DVBootLoader_edt.setTextColor(Color.GREEN);
		DVBootLoader_edt.setText(android.os.Build.BOOTLOADER);
		
		DVCPUabi = (TextView) findViewById(R.id.showCPUabi);
		DVCPUabi_edt = (TextView) findViewById(R.id.deviceCPUabi);
		DVCPUabi_edt.setTextColor(Color.GREEN);
		DVCPUabi_edt.setText(android.os.Build.CPU_ABI);
		
		DVCPUabi2 = (TextView) findViewById(R.id.showCPUabi2);
		DVCPUabi2_edt = (TextView) findViewById(R.id.deviceCPUabi2);
		DVCPUabi2_edt.setTextColor(Color.GREEN);
		DVCPUabi2_edt.setText(android.os.Build.CPU_ABI2);
		
		DVDisplay = (TextView) findViewById(R.id.showDisplay);
		DVDisplay_edt = (TextView) findViewById(R.id.deviceDisplay);
		DVDisplay_edt.setTextColor(Color.GREEN);
		DVDisplay_edt.setText(android.os.Build.DISPLAY);
		
		DVGL_Renderer = (TextView) findViewById(R.id.showGLRenderer);
		DVGL_Renderer_edt = (TextView) findViewById(R.id.deviceGLRenderer);
		DVGL_Renderer_edt.setTextColor(Color.GREEN);
		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setRenderer(glVersion);
		
		DVGL_Vendor = (TextView) findViewById(R.id.showGLVendor);
		DVGL_Vendor_edt = (TextView) findViewById(R.id.deviceGLVendor);
		DVGL_Vendor_edt.setTextColor(Color.GREEN);
		
		DVGL_Version = (TextView) findViewById(R.id.showGLVersion);
		DVGL_Version_edt = (TextView) findViewById(R.id.deviceGLVersion);
		DVGL_Version_edt.setTextColor(Color.GREEN);
		
		DVPgkGames = (TextView) findViewById(R.id.showGlassworkPgkNames);
		DVPgkGames_edt = (TextView) findViewById(R.id.GWPgkGames);
		/*PACKAGE_NAME = getApplicationContext().getPackageName();
		DVPgkGames_edt.setText(PACKAGE_NAME);*/
		
		DVSerial = (TextView) findViewById(R.id.showSerial);
		DVSerial_edt = (TextView) findViewById(R.id.deviceSerial);
		DVSerial_edt.setTextColor(Color.GREEN);
		DVSerial_edt.setText(android.os.Build.SERIAL);
		
		DVLogcat = (TextView) findViewById(R.id.showLogcat);
		DVLogcat_edt = (TextView) findViewById(R.id.logcatFeed);
		DVLogcat_edt.setMovementMethod(ArrowKeyMovementMethod.getInstance());

		rlLayout.addView(glSurfaceView);
		
		packageManager = getPackageManager();
        List<PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);
 
        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();
 
        /*To filter out System apps*/
        for(PackageInfo pi : packageList) {
            boolean b = isSystemPackage(pi);
            if(!b) {
                packageList1.add(pi);
            }
        }
        apkList = (ListView) findViewById(R.id.applist);
        apkList.setAdapter(new ApkAdapter(this, packageList1, packageManager));
 
        apkList.setOnItemClickListener(this);
		//waiting to create a button which will call another activity to show logcat
			/*try{
				Process process = Runtime.getRuntime().exec("logcat -d");
				//Process process = Runtime.getRuntime().exec("logcat -s Unity");
				BufferedReader bufferReader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				StringBuilder log = new StringBuilder();
				String line = "";
				while ((line = bufferReader.readLine()) != null) {
					log.append(line);
				}
				DVLogcat_edt.setText(log.toString());
			}catch (IOException e){
				System.out.println("Error on: " + e);
			}*/
	}
	
	/**
     * Return whether the given PackgeInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param pkgInfo
     * @return boolean
     */
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long row) {
        PackageInfo packageInfo = (PackageInfo) parent
                .getItemAtPosition(position);
        AppData appData = (AppData) getApplicationContext();
        appData.setPackageInfo(packageInfo);
 
        Intent appInfo = new Intent(getApplicationContext(), ApkInfo.class);
        startActivity(appInfo);
    }
    
	public void logcatViewClick(View V){
		Intent intent = new Intent(this, LogcatViewer.class);
		startActivity(intent);
	}
	
	// send logcat button on main page
	public void sendEmailClick(View v) {
		Intent intent = new Intent(this, EmailSend.class);
		startActivity(intent);
		
	}
	
	public void showZGames(View v) {
		Intent intent = new Intent(this, ApkListActivity.class);
		startActivity(intent);
		
	}
	
	// installed apps button on main page
	public void installedAppsClick(View v) {
		Intent intent = new Intent(this, EmailSend.class);
		startActivity(intent);
			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gwmain, menu);
		return true;
	}

}
