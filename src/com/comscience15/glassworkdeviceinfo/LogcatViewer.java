package com.comscience15.glassworkdeviceinfo;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class LogcatViewer extends ListActivity{
	private LogStringAdaptor adaptor = null;
	private ArrayList<String> logarray = null;
	private LogReaderTask logReaderTask = null;
	Button bEmailLogcat, bClearLogcat, bSaveLogcat;
	String toEmailPeople = "nsrileeannop@zynga.com";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logcat_view);
		
		logarray = new ArrayList<String>();
		adaptor = new LogStringAdaptor(this, R.id.txtLogString, logarray);
		
		setListAdapter(adaptor);
		
		logReaderTask = new LogReaderTask();
		
		logReaderTask.execute();

		//Intent intent = getIntent();
		/*try{
			bClearLogcat.setOnClickListener(this);
			bSaveLogcat.setOnClickListener(this);
			bEmailLogcat.setOnClickListener(this);
		}catch (NullPointerException e){
			e.printStackTrace();
		}*/
	}
	
	public void emailClick(View v) {
		/*switch(v.getId()){
		case R.id.clearLogcat:
			
			break;
		case R.id.emailLogcat:
			Intent intentEmailSend = new Intent(this, sendEmail.class);
			startActivity(intentEmailSend);
			break;
		case R.id.saveLogcat:
			
			break;
		}*/
		//Intent intent = new Intent(this, sendEmail.class);
		Intent intent = new Intent(this, EmailSend.class);
		startActivity(intent);
		
	}
	public void clearClick(View v) {
		
	}
	public void saveClick(View v) {
		/*String fileName = "logcat_"+System.currentTimeMillis()+".txt";
	    File outputFile = new File(context.getExternalCacheDir(),fileName);
	    @SuppressWarnings("unused")
	    Process process = Runtime.getRuntime().exec("logcat -f "+outputFile.getAbsolutePath());*/
	}
	
	@Override
	protected void onDestroy() {
		logReaderTask.stopTask();
		
		super.onDestroy();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(LogcatViewer.this);
		String text = ((String) ((TextView)v).getText());
		
		builder.setMessage(text);
		
		builder.show();
	}
	
	private int getLogColor(String type) {
		int color = Color.BLUE;
		
		if(type.equals("D")){
			color = Color.rgb(0,0,200);
		}else if(type.equals("W")){
			color = Color.rgb(128, 0, 0);
		}else if(type.equals("E")){
			color = Color.rgb(255, 0, 0);
		}else if(type.equals("I")){
			color = Color.rgb(0, 128, 0);
		}
		return color;
	}
	private class LogStringAdaptor extends ArrayAdapter<String> {
		private List<String> objects = null;
		
		public LogStringAdaptor(Context context, int textviewid, List<String> objects) {
			super(context, textviewid, objects);
			this.objects = objects;
		}
		
		@Override
		public int getCount() {
			return ((null != objects) ? objects.size() : 0);
		}
		
		@Override
		public long getItemId(int position){
			return position;
		}
		
		@Override
		public String getItem(int position){
			return ((null != objects) ? objects.get(position) : null);
		}
		
		public View getView(int position, View converView, ViewGroup parant) {
			View view = converView;
			
			if(null == view){
				LayoutInflater vi = (LayoutInflater)LogcatViewer.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.logitem, null);
			}
			
			String data = objects.get(position);
			
			if(null != data){
				TextView textview = (TextView)view.findViewById(R.id.txtLogString);
				String type = data.substring(0,1);
				String line = data.substring(2);
				
				textview.setText(line);
				textview.setTextColor(getLogColor(type));
			}
			return view;
		}
	}
	
	private class LogReaderTask extends AsyncTask<Void, String, Void> {
		private final String LOGCAT_CMD = new String("logcat -v time");
		private final int BUFFER_SIZE = 1024;
		private boolean isRunning = true;
		private Process logprocess = null;
		private BufferedReader reader = null;
		private String[] line = null;
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				logprocess = Runtime.getRuntime().exec(LOGCAT_CMD);
			}catch (IOException e){
				e.printStackTrace();
				isRunning = false;
			}
			
			try{
				reader = new BufferedReader(new InputStreamReader(logprocess.getInputStream()),BUFFER_SIZE);
			}catch(IllegalArgumentException e){
				e.printStackTrace();
				isRunning = false;
			}
			
			line = new String[1];
			
			try{
				while(isRunning){
					line[0] = reader.readLine();
					publishProgress(line);
				}
			}catch(IOException e){
				e.printStackTrace();
				isRunning = false;
			}
			return null;
		}
		
		@Override
		protected void onCancelled(){
			super.onCancelled();
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
		}
		
		@Override
		protected void onProgressUpdate(String... values){
			super.onProgressUpdate(values);
			adaptor.add(values[0]);
		}
		
		public void stopTask(){
			isRunning = false;
			logprocess.destroy();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}