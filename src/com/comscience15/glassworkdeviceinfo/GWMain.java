package com.comscience15.glassworkdeviceinfo;

import java.util.ArrayList;
import java.util.List;

import com.comscience15.glassworkdeviceinfo.ApkInfo;
import com.comscience15.glassworkdeviceinfo.adapter.ApkAdapter;
import com.comscience15.glassworkdeviceinfo.app.AppData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.method.ArrowKeyMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GWMain extends Activity implements OnItemClickListener {

	private TextView DVBrand, DVModel, DVAndroidOS, DVPgkGames,DVBrand_edt, DVModel_edt, DVAndroidOS_edt, DVPgkGames_edt, DVLogcat, DVLogcat_edt, DVInstalledApps;
    PackageManager packageManager;
    ListView apkList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gwmain);
		
		DVBrand = (TextView) findViewById(R.id.showBrand);
		DVBrand_edt = (TextView) findViewById(R.id.deviceBrand);
		DVBrand_edt.setText((android.os.Build.MANUFACTURER).toUpperCase());
		
		DVModel = (TextView) findViewById(R.id.showModel);
		DVModel_edt = (TextView) findViewById(R.id.deviceModel);
		DVModel_edt.setText(android.os.Build.MODEL);
		
		DVAndroidOS = (TextView) findViewById(R.id.showOS);
		DVAndroidOS_edt = (TextView) findViewById(R.id.deviceOS);
		DVAndroidOS_edt.setText(android.os.Build.VERSION.RELEASE);
		
		DVPgkGames = (TextView) findViewById(R.id.showGlassworkPgkNames);
		DVPgkGames_edt = (TextView) findViewById(R.id.GWPgkGames);
		/*PACKAGE_NAME = getApplicationContext().getPackageName();
		DVPgkGames_edt.setText(PACKAGE_NAME);*/
		
		DVLogcat = (TextView) findViewById(R.id.showLogcat);
		DVLogcat_edt = (TextView) findViewById(R.id.logcatFeed);
		DVLogcat_edt.setMovementMethod(ArrowKeyMovementMethod.getInstance());
		//DVInstalledApps = (TextView) findViewById(R.id.installedApps);
		
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
		//intent.putExtra("screenText", "Hello World!");
		startActivity(intent);
	}
	
	// send logcat button on main page
	public void sendEmailClick(View v) {
		Intent intent = new Intent(this, EmailSend.class);
		startActivity(intent);
		
	}
	
//	// installed apps button on main page
//	public void installedAppsClick(View v) {
//		Intent intent = new Intent(this, EmailSend.class);
//		startActivity(intent);
//			
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gwmain, menu);
		return true;
	}

}
