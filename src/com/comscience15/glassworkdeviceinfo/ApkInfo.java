package com.comscience15.glassworkdeviceinfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.comscience15.glassworkdeviceinfo.R;
import com.comscience15.glassworkdeviceinfo.app.AppData;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class ApkInfo extends Activity {
 
    TextView appLabel, packageName, version, features, permissions, andVersion, installed, lastModify, path;
//    ImageView appicon;
    PackageInfo packageInfo;
    PackageManager pm;
    Button bLaunchApp;
 
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
        features = (TextView) findViewById(R.id.req_feature);
        permissions = (TextView) findViewById(R.id.req_permission);
        andVersion = (TextView) findViewById(R.id.andversion);
        path = (TextView) findViewById(R.id.path);
        installed = (TextView) findViewById(R.id.insdate);
        lastModify = (TextView) findViewById(R.id.last_modify);
        bLaunchApp = (Button) findViewById(R.id.lApp);
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
 
        // features
        if (packageInfo.reqFeatures != null)
            features.setText(getFeatures(packageInfo.reqFeatures));
        else
            features.setText("-");
 
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
    
    //launch App
    public void launchAppClick(View V){
    	String appName = (String) packageInfo.packageName;
    	Log.d("COMSCIENCE15", "App Name is: " + appName);
    	PackageManager manager = getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent = manager.getLaunchIntentForPackage(appName);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
	}
    
}
