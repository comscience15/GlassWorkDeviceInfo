package com.comscience15.glassworkdeviceinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class sendEmail extends Activity implements View.OnClickListener {

	EditText personsEmail, subject, content;
	String emailAdd, subjectLine, contentLine;
	Button sendEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_layout);
		initializeVars();
		sendEmail.setOnClickListener(this);
	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		personsEmail = (EditText) findViewById(R.id.etEmails);
		subject = (EditText) findViewById(R.id.etSubject);
		content = (EditText) findViewById(R.id.etContent);
		sendEmail = (Button) findViewById(R.id.bSentEmail);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated();
		String emailaddress[] = { emailAdd };
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subjectLine);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, contentLine);
		startActivity(emailIntent);

	}

	private void convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated() {
		// TODO Auto-generated method stub
		emailAdd = personsEmail.getText().toString();
		subjectLine = subject.getText().toString();
		contentLine = content.getText().toString();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}