package com.example.android.BluetoothChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class File_look extends Activity {

	TextView textMsg, textPrompt;
	String str;
	EditText edText1;

	String textSource = "http://www.thissite.hol.es/uploads/";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("file download", "started");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filelook);
		Intent i = getIntent();
		String s1 = (String) i.getExtras().get("name");

		textSource = textSource + s1 + ".txt";
		Log.i("data rec", s1);

		textPrompt = (TextView) findViewById(R.id.textprompt);
		textMsg = (TextView) findViewById(R.id.textmsg);

		textPrompt.setText("Wait...");

		Log.i("layout", "changed");

		URL textUrl;
		try {
			Log.i("info", "download started");
			textUrl = new URL(textSource);
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(textUrl.openStream()));
			String StringBuffer;
			String stringText = "";
			while ((StringBuffer = bufferReader.readLine()) != null) {
				stringText += StringBuffer;
			}
			bufferReader.close();
			textMsg.setText(stringText);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			textMsg.setText(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			textMsg.setText(e.toString());
		}

		textPrompt.setText("\n\nFinished!");

	}
}
