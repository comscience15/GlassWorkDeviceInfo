package com.comscience15.glassworkdeviceinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comscience15.glassworkdeviceinfo.R;
import com.comscience15.glassworkdeviceinfo.app.AppData;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint({ "NewApi", "SdCardPath" })
public class ApkInfo extends Activity {
 
    private static final String ImageUtils = null;
	TextView appLabel, packageName, version, features, permissions, andVersion, installed, lastModify, path, appMem_Name;
//    ImageView appicon;
    PackageInfo packageInfo;
    PackageManager pm;
    Button bLaunchApp,bResetBlob, bEmailBlob;
//    Button bLSs;
	int i = 0;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apkinfo);
 
        findViewsById();
 
        AppData appData = (AppData) getApplicationContext();
        packageInfo = appData.getPackageInfo();
 
        setValues();
 
    }
 
    private void findViewsById() {
//    	appicon = (ImageView) findViewById(R.id.appiconimage);
    	appLabel = (TextView) findViewById(R.id.applabel);
        packageName = (TextView) findViewById(R.id.package_name);
        version = (TextView) findViewById(R.id.version_name);
//        features = (TextView) findViewById(R.id.req_feature);
        permissions = (TextView) findViewById(R.id.req_permission);
        andVersion = (TextView) findViewById(R.id.andversion);
        path = (TextView) findViewById(R.id.path);
        installed = (TextView) findViewById(R.id.insdate);
        lastModify = (TextView) findViewById(R.id.last_modify);  
        bLaunchApp = (Button) findViewById(R.id.lApp);
        bResetBlob = (Button) findViewById(R.id.resetBlob);
        bEmailBlob = (Button) findViewById(R.id.emailBlob);
//        bLSs = (Button) findViewById(R.id.lSS);
        appMem_Name = (TextView) findViewById(R.id.appMem_Name);
    }
    
    @SuppressLint("NewApi")
    private void setValues() {
        // app icon
//    	Drawable appIcon = pm.getApplicationIcon(packageInfo.applicationInfo);
    	
    	// APP name
        appLabel.setText(getPackageManager().getApplicationLabel(
                packageInfo.applicationInfo));
 
        // package name
        packageName.setText(packageInfo.packageName);
 
        // version name
        version.setText(packageInfo.versionName);
 
        // target version
        andVersion.setText(Integer
                .toString(packageInfo.applicationInfo.targetSdkVersion));
 
        // path
        path.setText(packageInfo.applicationInfo.sourceDir);
 
        // first installation
        installed.setText(setDateFormat(packageInfo.firstInstallTime));
 
        // last modified
        lastModify.setText(setDateFormat(packageInfo.lastUpdateTime));
 
//        // TAKE OUT since most of app doesn't have ---- Required features
//        if (packageInfo.reqFeatures != null)
//            features.setText(getFeatures(packageInfo.reqFeatures));
//        else
//            features.setText("-");
 
        //set specific application memory usage
//        String appName = (String) packageInfo.packageName;
//		String cmd = "adb shell dumpsys meminfo " + appName;
//		OutputStream process = null;
//		try {
//			process = Runtime.getRuntime().exec(cmd).getOutputStream();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		Toast.makeText(ApkInfo.this, "REMINDER: dumpsys cannot be retrieved programmatically by a normal application. Unless your application is signed with a platform key or built as a system application, dumpsys information cannot be retrieved programmatically.", Toast.LENGTH_LONG).show();
//		appMem_Name.setText(process.toString());
        
        // uses-permission
        if (packageInfo.requestedPermissions != null)
            permissions
                    .setText(getPermissions(packageInfo.requestedPermissions));
        else
            permissions.setText("-");
    }

	@SuppressLint("SimpleDateFormat")
    private String setDateFormat(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = formatter.format(date);
        return strDate;
    }
 
    // Convert string array to comma separated string
    private String getPermissions(String[] requestedPermissions) {
        String permission = "";
        for (int i = 0; i < requestedPermissions.length; i++) {
            permission = permission + requestedPermissions[i] + ",\n";
        }
        return permission;
    }
 
    // Convert string array to comma separated string
    private String getFeatures(FeatureInfo[] reqFeatures) {
        String features = "";
        for (int i = 0; i < reqFeatures.length; i++) {
            features = features + reqFeatures[i] + ",\n";
        }
        return features;
    }
    
    //launch selected App
    public void launchAppClick(View V){
    	String appName = (String) packageInfo.packageName;
    	Log.d("COMSCIENCE15", "App Name is: " + appName);
    	PackageManager manager = getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent = manager.getLaunchIntentForPackage(appName);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
	}
    
    //reset game user blob
	public void ResetUserBlob(View V){
    	String appPgkName = (String) packageInfo.packageName;
    	String appName = (String) getPackageManager().getApplicationLabel(packageInfo.applicationInfo);
    	Log.d("COMSCIENCE15", "App Name is: " + appName);
    	int pid = 0;
    	ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

//    	trimCache(this);
    	try {
    		File userblob = new File(Environment.getExternalStorageDirectory().getPath()+"/Android/data/" + appPgkName + "/files/UserBlob");
    		File userblob_bak = new File(Environment.getExternalStorageDirectory().getPath()+"/Android/data/" + appPgkName + "/files/UserBlob_bak");
    		boolean successDelete = userblob.delete();
    		boolean successDelete1 = userblob_bak.delete();
    		Toast.makeText(ApkInfo.this, "!!! PLEASE MAKE SURE FORCE CLOSE " + appName + " BEFORE RELAUNCH IT !!!", Toast.LENGTH_LONG).show();
    		
//    		//"DOES NOT WORK"   ---   kill current running Zynga apps
//    		List<ActivityManager.RunningAppProcessInfo> pids = am.getRunningAppProcesses();
//    		for (int i = 0; i < pids.size(); i++) {
//    			ActivityManager.RunningAppProcessInfo info = pids.get(i);
//    			if (info.processName.equalsIgnoreCase("com.comscience15.glassworkdeiceinfo")){
//    				pid = info.pid;
//    				Log.d("COMSCIENCE15 ", info.toString());
//    				Log.d("TRY "+ i, " " + pid);
//    				android.os.Process.killProcess(pid);
//    			}
//    		}
//    		android.os.Process.killProcess(pid);
    		
    		if (!successDelete && !successDelete1){
    			Toast.makeText(ApkInfo.this, "Unable to delete " + appName + "'s UserBlob, UserBlob_bak, JSONBlob", Toast.LENGTH_LONG).show();
    		}	
    	} catch (Exception e){
    		Log.e("COMSCIENCE15", e.getMessage());
    	}
	}

//	private void trimCache(Context context) {
//		// TODO Auto-generated method stub
//		try{
//			File dir = context.getCacheDir();
//			if (dir != null && dir.isDirectory()){
//				deleteDir(dir);
//			}
//		} catch (Exception e){
//			
//		}
//	}

	private static boolean deleteDir(File dir) {
		// TODO Auto-generated method stub
		if (dir != null && dir.isDirectory()){
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean sucess = deleteDir(new File(dir, children[i]));
				if (!sucess) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	//Send email a blob
    public void emailBlob(View V){
    	String appName = (String) getPackageManager().getApplicationLabel(packageInfo.applicationInfo);
    	String appPathName = (String) packageInfo.packageName;
//    	String[] to = nsrileeannop@zynga.com, mkellogg@zynga.com, jquilo@zynga.com,mgray@zynga.com;
    	Uri uri = Uri.fromFile(new File("/sdcard/Android/data/"+ appPathName+"/files/UserBlob"));
    	Log.d("COMSCIENCE15", uri+" path ");
    	
    	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
        emailIntent.setType("text/html");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"nsrileeannop@zynga.com"}); 
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, appName + " Userblob"); 
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "This is " + appName + " userblob"); 
//      Log.v(getClass().getSimpleName(), "sPhotoUri=" + Uri.parse("file:/"+ sPhotoFileName));
        
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
    
  //launch selected App and take SS in 5 second
//    public void launchAndSSClick(View V){
//
//    	final String appName = (String) getPackageManager().getApplicationLabel(packageInfo.applicationInfo);
//    	Log.d("COMSCIENCE15", "App Name is: " + appName);
//    		
//		//inform user to wait for 5 sec to get SS start
//		Toast.makeText(ApkInfo.this, "Screenshot of " + appName + " will be started in 5 seconds", Toast.LENGTH_LONG).show();
//		
//		new Handler().postDelayed(new Runnable() {
//			
//			//after tap screenshot button will be delayed for 5 second before taking SS
//			@Override
//			public void run() {  
//				View view = findViewById(android.R.id.content).getRootView();
//				view.setDrawingCacheEnabled(true);
//                Bitmap bitmap = view.getDrawingCache();
//				
//				String appPkg = (String) packageInfo.packageName;
//		    	Log.d("COMSCIENCE15", "App Name is: " + appPkg);
//		    	PackageManager manager = getPackageManager();
//				Intent intent = new Intent(Intent.ACTION_MAIN);
//				intent = manager.getLaunchIntentForPackage(appPkg);
//				intent.addCategory(Intent.CATEGORY_LAUNCHER);
//				startActivity(intent);
//
//                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//        			   //check if external storage is available, otherwise error message display
//        		       
//        		       File mPath = Environment.getExternalStorageDirectory();
//        		       File directory = new File (mPath.getAbsolutePath() + File.separator + "Zynga_ScreenShots");
//        		       directory.mkdirs();
//        		       String filename = appName + System.currentTimeMillis() + ".jpeg"; 
//        			   File imageFile = new File(directory, filename);
//
//	        			   while (imageFile.exists()){
//	        			    i++;   
//	        		    	imageFile = new File(directory, filename);
//		        		   } 
//        		       
//        		       if (!imageFile.exists()) {
//        		    	   if (directory.canWrite())
//        		    	   {
//        		    		   try {
//        		    		   	 FileOutputStream out = new FileOutputStream(imageFile, true);
//        		    		     bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//        		    		     out.flush();
//        		    		     out.close();
//        		    		     Toast.makeText(ApkInfo.this, "File exported to /sdcard/Zynga_ScreenShots/" + appName + i + System.currentTimeMillis() + ".jpg", Toast.LENGTH_SHORT).show();
//        		    		     i++;
//        						} catch (IOException e) {
//        							e.printStackTrace();
//        					    }  
//        		    	   }
//        		       }
//        		       
//         			}else{
//         				Toast.makeText(ApkInfo.this, "SD Card not available!", Toast.LENGTH_SHORT).show();
//        		    }
//			}
//		},5000);
//	}
}
