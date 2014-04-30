package com.example.android.BluetoothChat;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

public class Fileshare extends Activity {

	String deviceAddress;
	String deviceName;
	String path;
	ImageView im1;
	BluetoothAdapter mBluetoothAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        setContentView(R.layout.activity_nfc);
        
        im1 = (ImageView)findViewById(R.id.imageView1);
        
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBluetoothAdapter.isEnabled())
        {
        	mBluetoothAdapter.enable();
        }
        
        //deviceAddress = getBluetoothMacAddress();
        //deviceAddress = "2C:44:01:35:74:93";	//n
       // deviceAddress = "2C:44:01:A8:6F:56";	//v
        deviceAddress = "5C:17:D3:F4:82:01";
       
        //deviceAddress = "0C:71:5D:36:BC:2E";
        //deviceAddress = "8C:64:22:0D:E4:15";	//s
        
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nfc, menu);
        return true;
        
    }

    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            Uri photoUri = intent.getData();

            if (photoUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this
                        .getContentResolver(), photoUri);
                    im1.setImageBitmap(bitmap);
                    path = photoUri.toString();
                    //path = "content://mnt/ext_card/bluetooth/tt.pdf";
                    bluetoothShare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
  
    public static String getBluetoothMacAddress() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
     
        // if device does not support Bluetooth
        if(mBluetoothAdapter==null){
          //  Log.d(TAG,"device does not support bluetooth");
            return null;
        }
         
        return mBluetoothAdapter.getAddress();
    }

    public static String getLocalBluetoothName() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
     
        // if device does not support Bluetooth
        if(mBluetoothAdapter==null){
    //        Log.d(TAG,"device does not support bluetooth");
            return null;
        }
         
        return mBluetoothAdapter.getName();
    }
    
    public void bluetoothShare()
    {
       // deviceName = getLocalBluetoothName();
    	
    	int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    	
        if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Intent sharingIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
            sharingIntent.setType("image/jpeg");
            sharingIntent
                    .setComponent(new ComponentName(
                            "com.android.bluetooth",
                            "com.android.bluetooth.opp.BluetoothOppLauncherActivity"));
            sharingIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(sharingIntent);
        } else 
        {
    
        
        Toast.makeText(this, "Bluetooth Device = "+deviceAddress, Toast.LENGTH_LONG).show();
        Toast.makeText(this, ""+path, Toast.LENGTH_LONG).show();
        
        ContentValues values = new ContentValues();
        values.put(BluetoothShare.URI, path);
        values.put(BluetoothShare.DESTINATION, deviceAddress);
        values.put(BluetoothShare.DIRECTION, BluetoothShare.DIRECTION_OUTBOUND);
        Long ts = System.currentTimeMillis();
        values.put(BluetoothShare.TIMESTAMP, ts);
        getContentResolver().insert(BluetoothShare.CONTENT_URI, values);
        }

    }
 
    @Override
    public void onPause()
    {
    	super.onPause();
    	
    	/*
    	
    	if(mBluetoothAdapter.isEnabled())
    	{
    		mBluetoothAdapter.disable();
    	} 
    	
    	   	*/
    }
}

