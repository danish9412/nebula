package com.example.android.BluetoothChat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.ads.*;
import com.google.*;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends Activity implements OnClickListener {
	Button bgo;
	Button upl;
	Button filebutton;
	Button subt;
	EditText subm;
	EditText ed1;
	// String blueAddress;
	// String uploadFileName;

	// EditText textBox;
	static final int READ_BLOCK_SIZE = 100;

	Button createList;
	String munni;

	final String MEDIA_PATH = "/storage/sdcard0/Download/"; // Danish mobile
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private String mp3Pattern = ".mp3";

	private ArrayList<HashMap<String, String>> pappu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		bgo = (Button) findViewById(R.id.button1);
		upl = (Button) findViewById(R.id.button2);
		filebutton = (Button) findViewById(R.id.button3);
		subm = (EditText) findViewById(R.id.editText1);
		ed1 = (EditText) findViewById(R.id.editText1);

		// textBox = (EditText) findViewById(R.id.txtText1);

		createList = (Button) findViewById(R.id.scanmp3);
		// createList.setOnClickListener(this);

		InputStream is = this.getResources().openRawResource(R.raw.littleboy);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// Bluetooth mac address code
		/*
		 * BluetoothAdapter blueMac=BluetoothAdapter.getDefaultAdapter();
		 * if(blueMac==null) { Toast.makeText(getBaseContext(),
		 * "Turn bluetooth on", Toast.LENGTH_SHORT).show(); }
		 * 
		 * blueAddress = blueMac.getAddress();
		 */
		// uploadFileName="littleboy.txt";
		// Toast.makeText(getBaseContext(), , Toast.LENGTH_SHORT).show();

		// String str = null;
		/*
		 * try { while ((str = br.readLine()) != null) {
		 * Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT) .show(); }
		 * is.close(); br.close(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
	}

	public void onClick(View v) {
		Intent i = new Intent("com.example.android.BluetoothChat.BluetoothChat");
		startActivity(i);
	}

	public void upld(View v) {
		Intent i = new Intent(
				"com.example.android.BluetoothChat.UploadToServer");
		// i.putExtra("BluetoothAddress", uploadFileName);
		startActivity(i);
	}

	public void flook(View v) {

		Intent i = new Intent("com.example.android.BluetoothChat.File_look");

		i.putExtra("name", ed1.getText().toString());
		Log.i("data", ed1.getText().toString());
		startActivity(i);

	}

	public void createMp3List(View v) {

		Toast.makeText(getBaseContext(), "function called !",
				Toast.LENGTH_SHORT).show();

		munni = getPlayList().toString();
		Log.d(munni, "ankit chaurasia");

		Toast.makeText(getBaseContext(), "string catched!", Toast.LENGTH_SHORT)
				.show();

		// String str = textBox.getText().toString();
		String str = munni;
		try {
			// ---SD Card Storage---
			File sdCard = Environment.getExternalStorageDirectory();
			File directory = new File(sdCard.getAbsolutePath() + "/LittleBoy");
			directory.mkdirs();
			File file = new File(directory, "littleboy.txt");
			FileOutputStream fOut = new FileOutputStream(file);

			/*
			 * FileOutputStream fOut = openFileOutput("textfile.txt",
			 * MODE_WORLD_READABLE);
			 */

			OutputStreamWriter osw = new OutputStreamWriter(fOut);

			// ---write the string to the file---
			osw.write(str);
			osw.flush();
			osw.close();

			// ---display file saved message---
			Toast.makeText(getBaseContext(), "File saved successfully!",
					Toast.LENGTH_SHORT).show();

			// ---clears the EditText---
			// textBox.setText("");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Function to read all mp3 files and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList() {
		// System.out.println(MEDIA_PATH);
		if (MEDIA_PATH != null) {
			File home = new File(MEDIA_PATH);
			File[] listFiles = home.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					// System.out.println(file.getAbsolutePath());
					if (file.isDirectory()) {
						scanDirectory(file);
					} else {
						addSongToList(file);
					}
				}
			}
		}
		// return songs list array
		return songsList;
	}

	private void scanDirectory(File directory) {
		if (directory != null) {
			File[] listFiles = directory.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					if (file.isDirectory()) {
						scanDirectory(file);
					} else {
						addSongToList(file);
					}

				}
			}
		}
	}

	private void addSongToList(File song) {
		if (song.getName().endsWith(mp3Pattern)) {
			HashMap<String, String> songMap = new HashMap<String, String>();

			songMap.put("\n",
					song.getName().substring(0, (song.getName().length() - 4)));
			// songMap.put("songPath", song.getPath());

			// Adding each song to SongList
			songsList.add(songMap);
		}
	}

}
