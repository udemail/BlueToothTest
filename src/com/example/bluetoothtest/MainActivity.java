package com.example.bluetoothtest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

//-----------------------
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
//import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	public static final int USER_CONFIRMATION_CONFIRMED = 1;    
	public static final String USER_CONFIRMATION = "confirm";
	static final String targetAddr = "00:12:FE:EB:7E:68";//wll
	static final String targetAddrUdemail = "E6:68:46:63:3F:54";
	static final String FilePath = "file:///sdcard/test.jpg";//"file:///sdcard/test.jpg";
	Button btnSearch, btnDis, btnExit;
	ToggleButton tbtnSwitch;
	ListView lvBTDevices;
	ArrayAdapter<String> adtDevices;
	List<String> lstDevices = new ArrayList<String>();
	BluetoothAdapter btAdapt;
	public static BluetoothSocket btSocket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     // 初始化本机蓝牙功能        
    	btAdapt = BluetoothAdapter.getDefaultAdapter();                
    	BluetoothDevice btDev = btAdapt.getRemoteDevice(targetAddrUdemail);        
    	try {            
    	Method m = null;            
    	try {                
    	m = btDev.getClass().getMethod("createRfcommSocket", new Class[] { int.class });            
    	} catch (SecurityException e) {                
    	e.printStackTrace();            
    	} catch (NoSuchMethodException e) 
    	{                
    	e.printStackTrace();            
    	}            
    	try {                
    	btSocket = (BluetoothSocket) m.invoke(btDev, 1);            
    	} catch (IllegalArgumentException e) {                
    	e.printStackTrace();            
    	} catch (IllegalAccessException e) {                
    	e.printStackTrace();            
    	} catch (InvocationTargetException e) {                
    	e.printStackTrace();            
    	}                        
    	//发送蓝牙配对            
    	btSocket.connect();            
    	/*             * 发送一指定的文件到其它蓝牙设备             */            
    	ContentValues cv = new ContentValues();            
    	cv.put("uri", FilePath);            
    	cv.put("destination", targetAddrUdemail);            
    	cv.put("direction", 0);            
    	Long ts = System.currentTimeMillis();            
    	cv.put("timestamp", ts);            
    	getContentResolver().insert(Uri.parse("content://com.android.bluetooth.opp/btopp"),cv);                        
    	btSocket.close();                    
    	} catch (IOException e) {            
    	e.printStackTrace();        
    	}   
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	//static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

	 


}
