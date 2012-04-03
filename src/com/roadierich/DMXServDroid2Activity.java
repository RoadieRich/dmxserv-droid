package com.roadierich;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

public class DMXServDroid2Activity extends Activity {
	
	NetworkingThread netThread = new NetworkingThread();
	
	SceneDelta currentDelta = null;
	 String ipAddress;
	public LinkedList<Map<Integer, Integer>> scenes = new LinkedList<Map<Integer,Integer>>();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Dialog ipDialogue = new Dialog(getApplicationContext());
        ipDialogue.setContentView(R.layout.ip_dialogue);
        ipDialogue.setTitle("Connecting to Server");
        
       
        
        Button btnOK = (Button) findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				ipAddress = ((EditText) findViewById(R.id.ipAddress)).getText().toString();
				ipDialogue.dismiss();
			}
		});
		
		ipDialogue.show();
		
		try {
			netThread.setIP(InetAddress.getByAddress(ipAddress.getBytes()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        netThread.run();
        
        SceneList scenes = new SceneList();
        scenes.generateFromXML(getSceneXmlFile());
        
        OnClickListener btnListner = new OnClickListener() {
			
			public void onClick(View v) {
				ToggleButton btn = (ToggleButton) v;
				if (currentDelta == null)
				{
					currentDelta = new SceneDelta();
				}
				Scene s = (Scene) btn.getTag();
				int level = btn.isChecked()? 255: 0;
				currentDelta.addChange(s.id, level, s.time);
				
			}
		};
        
        
        Button b = (Button) findViewById(R.id.btnScene1);
        b.setTag(scenes.scenes.get(0));
        b.setOnClickListener(btnListner);			
        
        b = (Button) findViewById(R.id.btnScene2);
        b.setTag(scenes.scenes.get(1));
        b.setOnClickListener(btnListner);			
        
        b = (Button) findViewById(R.id.btnScene3);
        b.setTag(scenes.scenes.get(2));
        b.setOnClickListener(btnListner);			
        
        
        b = (Button) findViewById(R.id.btnScene4);
        b.setTag(scenes.scenes.get(3));
        b.setOnClickListener(btnListner);
        
        b = (Button) findViewById(R.id.btnScene5);
        b.setTag(scenes.scenes.get(4));
        b.setOnClickListener(btnListner);
        
        b = (Button) findViewById(R.id.btnScene6);
        b.setTag(scenes.scenes.get(5));
        b.setOnClickListener(btnListner);
        
        LinearLayout leftGroupLayout = (LinearLayout)findViewById(R.id.leftGroupLayout);
        LinearLayout rightGroupLayout = (LinearLayout)findViewById(R.id.rightGroupLayout);
        

        OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				MyVerticalSeekBar sb = (MyVerticalSeekBar) seekBar;
				if (currentDelta == null)
				{
					currentDelta = new SceneDelta();
				}
				currentDelta.addChange(sb.scene.id, progress);
				
			}
		};
        
        MyVerticalSeekBar vsb = new MyVerticalSeekBar(getApplicationContext());
        vsb.scene = scenes.scenes.get(6);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		leftGroupLayout.addView(vsb);
		
		vsb = new MyVerticalSeekBar(getApplicationContext());
        vsb.scene = scenes.scenes.get(7);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		leftGroupLayout.addView(vsb);
		
		vsb = new MyVerticalSeekBar(getApplicationContext());
        vsb.scene = scenes.scenes.get(8);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		leftGroupLayout.addView(vsb);
		
		vsb = new MyVerticalSeekBar(getApplicationContext());
        vsb.scene = scenes.scenes.get(9);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		rightGroupLayout.addView(vsb);
		
		vsb = new MyVerticalSeekBar(getApplicationContext());
        vsb.scene = scenes.scenes.get(10);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		rightGroupLayout.addView(vsb);
		
		vsb = new MyVerticalSeekBar(getApplicationContext());
        vsb.scene = scenes.scenes.get(11);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		rightGroupLayout.addView(vsb);
    }
    
    File getSceneXmlFile()
    {
    	if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
    	{
    		return null;
    	}
    	
    	File location = new File(getExternalFilesDir(null),"scene.xml");
    	if (!location.exists())
    	{
    		
    	}
    	
    	
    	return null;
    }
}

class MyVerticalSeekBar extends VerticalSeekBar
{

	public MyVerticalSeekBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public Scene scene;
	
}