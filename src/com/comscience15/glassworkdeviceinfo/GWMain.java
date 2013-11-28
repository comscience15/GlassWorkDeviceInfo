package com.comscience15.glassworkdeviceinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ArrowKeyMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class GWMain extends Activity {

	private TextView DVBrand, DVModel, DVAndroidOS, DVPgkGames,DVBrand_edt, DVModel_edt, DVAndroidOS_edt, DVPgkGames_edt, DVLogcat, DVLogcat_edt;
	
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

	public void buttonClick(View V){
//		Log.i(TAG, "Button Clicked");	
//		final TextView textView = (TextView) findViewById(R.id.textView);
//		
//		String text = textView.getText().toString();
//		
//		if (text.contains("World")){
//			textView.setText("Hello Android!");
//		}
//		else {
//			textView.setText("Hello World!");
//		}
		
		Intent intent = new Intent(this, LogcatViewer.class);
		intent.putExtra("screenText", "Hello World!");
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gwmain, menu);
		return true;
	}

}
