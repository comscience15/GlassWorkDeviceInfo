package com.comscience15.glassworkdeviceinfo;

import java.util.ArrayList;
import java.util.List;

import com.comscience15.glassworkdeviceinfo.R;
import com.comscience15.glassworkdeviceinfo.adapter.*;
import com.comscience15.glassworkdeviceinfo.app.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
 
public class ApkListActivity extends Activity
            implements OnItemClickListener {
 
    PackageManager packageManager;
    ListView apkList;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apklist);
 
        packageManager = getPackageManager();
        List<PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);
 
        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();
        
        /*To filter out System apps*/
        for(PackageInfo pi : packageList) {
//        	String pkg = pi.packageName;
        	boolean a = zyngaAppOrNot(pi);
//            boolean b = isSystemPackage(pi);
//            if(!b) {
//            	if (pkg.substring(0,9).startsWith("com.zynga"))
        	  if(a=true){
            		packageList1.add(pi);
            }
        }
        apkList = (ListView) findViewById(R.id.applist);
        apkList.setAdapter(new ApkAdapter(this, packageList1, packageManager));
 
        apkList.setOnItemClickListener(this);
    }

	/**
     * Return whether the given PackgeInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     * @param pkgInfo
     * @return boolean
     */
//    private boolean isSystemPackage(PackageInfo pkgInfo) {
//        return (((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)) ? true
//                : false;
//    }
    
    private boolean zyngaAppOrNot(PackageInfo pkgInfo){
    	PackageManager pm = getPackageManager();
    	String zyngaApp = pkgInfo.packageName;
    	String result = zyngaApp.substring(0, 9);
    	boolean zyngaApp_installed = false;
    	try{
    		if (result.equals("com.zynga")){
	    		pm.getPackageInfo(result, PackageManager.GET_ACTIVITIES);
	    		zyngaApp_installed = true;
    		}
    	}catch (PackageManager.NameNotFoundException e){
    		zyngaApp_installed = false;
    	}
    	return zyngaApp_installed;
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
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gwmain, menu);
        return true;
    }
}
