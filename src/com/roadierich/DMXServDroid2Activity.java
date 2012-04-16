package com.roadierich;

import java.io.*;
import java.net.*;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class DMXServDroid2Activity extends Activity
{

	NetworkingThread netThread = new NetworkingThread(this);

	SceneDelta currentDelta = null;
	String ipAddress = "";

	public SceneList scenes = new SceneList();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		
		
		netThread.run();

		try
		{
			scenes.generateFromXML(getSceneXmlFile());
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			Log.getStackTraceString(e1);
		}

		final ToggleButton[] leftButtons = new ToggleButton[3];
		final ToggleButton[] rightButtons = new ToggleButton[3];
		
		View.OnClickListener leftBtnListner = new View.OnClickListener()
		{

			public void onClick(View v)
			{
				ToggleButton btn = (ToggleButton) v;
				if (!btn.isEnabled())
					return;
				
				if (currentDelta == null)
				{
					currentDelta = new SceneDelta();
				}
				Scene s = (Scene) btn.getTag();
				int level = btn.isChecked() ? 255 : 0;
				currentDelta.addChange(s, level, s.time);
				/*for (int i=0; i < 3; i++)
				{
					leftButtons[i].setEnabled(false);
					if (!leftButtons[i].equals(btn))
						leftButtons[i].setChecked(false);
				}
				//btn.setEnabled(false);
				try
				{
					Thread.sleep(s.time*1000);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					Log.getStackTraceString(e);
				}
				finally
				{
					for (int i=0; i < 3; i++)
					{
						leftButtons[i].setEnabled(true);
					}
//					btn.setEnabled(true);
				}*/
			}
		};
		
		View.OnClickListener rightBtnListner = new View.OnClickListener()
		{

			public void onClick(View v)
			{
				ToggleButton btn = (ToggleButton) v;
				if (currentDelta == null)
				{
					currentDelta = new SceneDelta();
				}
				Scene s = (Scene) btn.getTag();
				int level = btn.isChecked() ? 255 : 0;
				currentDelta.addChange(s, level, s.time);
				/*for (int i=0; i < 3; i++)
				{
					rightButtons[i].setEnabled(false);
					if (!rightButtons[i].equals(btn))
						rightButtons[i].setChecked(false);
				}*/
				
				/*btn.setEnabled(false);
				try
				{
					Thread.sleep(s.time*1000);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					Log.getStackTraceString(e);
				}
				finally
				{
					for (int i=0; i < 3; i++)
					{
						rightButtons[i].setEnabled(true);
					}
					btn.setEnabled(true);
				}*/
			}
		};
		
		leftButtons[0] = (ToggleButton) findViewById(R.id.btnScene1);
		leftButtons[0].setTag(scenes.scenes.get(0));
		leftButtons[0].setOnClickListener(leftBtnListner);

		leftButtons[1] = (ToggleButton) findViewById(R.id.btnScene2);
		leftButtons[1].setTag(scenes.scenes.get(1));
		leftButtons[1].setOnClickListener(leftBtnListner);

		leftButtons[2] = (ToggleButton) findViewById(R.id.btnScene3);
		leftButtons[2].setTag(scenes.scenes.get(2));
		leftButtons[2].setOnClickListener(leftBtnListner);
		
		rightButtons[0] = (ToggleButton) findViewById(R.id.btnScene4);
		rightButtons[0].setTag(scenes.scenes.get(3));
		rightButtons[0].setOnClickListener(rightBtnListner);

		rightButtons[1] = (ToggleButton) findViewById(R.id.btnScene5);
		rightButtons[1].setTag(scenes.scenes.get(4));
		rightButtons[1].setOnClickListener(rightBtnListner);

		rightButtons[2] = (ToggleButton) findViewById(R.id.btnScene6);
		rightButtons[2].setTag(scenes.scenes.get(5));
		rightButtons[2].setOnClickListener(rightBtnListner);
		
		

		LinearLayout leftGroupLayout = (LinearLayout) findViewById(R.id.leftGroupLayout);
		LinearLayout rightGroupLayout = (LinearLayout) findViewById(R.id.rightGroupLayout);

		OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener()
		{

			public void onStopTrackingTouch(SeekBar seekBar)
			{
			}

			public void onStartTrackingTouch(SeekBar seekBar)
			{
			}

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{

				MyVerticalSeekBar sb = (MyVerticalSeekBar) seekBar;
				if (currentDelta == null)
				{
					currentDelta = new SceneDelta();
				}
				currentDelta.addChange(sb.scene, progress);
			}
		};
		

		LayoutParams lop = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);

		MyVerticalSeekBar vsb = new MyVerticalSeekBar(this);
		vsb.scene = scenes.scenes.get(6);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		leftGroupLayout.addView(vsb,lop);

		vsb = new MyVerticalSeekBar(this);
		vsb.scene = scenes.scenes.get(7);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		leftGroupLayout.addView(vsb, lop);

		vsb = new MyVerticalSeekBar(this);
		vsb.scene = scenes.scenes.get(8);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		leftGroupLayout.addView(vsb, lop);

		vsb = new MyVerticalSeekBar(this);
		vsb.scene = scenes.scenes.get(9);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		rightGroupLayout.addView(vsb,lop);

		vsb = new MyVerticalSeekBar(this);
		vsb.scene = scenes.scenes.get(10);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		rightGroupLayout.addView(vsb,lop);

		vsb = new MyVerticalSeekBar(this);
		vsb.scene = scenes.scenes.get(11);
		vsb.setOnSeekBarChangeListener(onSeekBarChangeListener);
		vsb.setMax(255);
		rightGroupLayout.addView(vsb, lop);

		Thread mainloop = new Thread(new Runnable()
		{

			public void run()
			{
				// TODO Auto-generated method stub
				while (true)
				{
					if((currentDelta != null) && (currentDelta.changes.size() > 0))
						netThread.queueData(currentDelta.toXML());
					try
					{
						Thread.sleep(50);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						Log.getStackTraceString(e);
					}
				}
			}
		});
		mainloop.start();
	}
	//[2012-04-13 23:00:32 - Emulator] WARNING: Data partition already in use. Changes will not persist!

	protected Dialog onCreateDialog(int id)
	{
		final Dialog dialog;
		final DMXServDroid2Activity parentActivity = this;
		switch (id)
		{
		case 1:
			dialog = new Dialog(this);
			dialog.setTitle("Connect to Server");
			dialog.setContentView(R.layout.ip_dialogue);
			dialog.setCancelable(false);
			((Button) dialog.findViewById(R.id.btnOk)).setOnClickListener(
					new View.OnClickListener()
					{
						public void onClick(View v)
						{
							synchronized (parentActivity.ipAddress)
							{
								parentActivity.ipAddress = ((EditText)dialog.findViewById(R.id.ipAddress))
										.getText().toString();
								
								dialog.dismiss();
								
								//parentActivity.ipAddress.notify();
							}
							
						}
					});
			break;
		default:
			return null;
		}
		return dialog;
	}
	
	@Override
	public void  onStart()
	{
		super.onStart();

		showDialog(1);
		
	}

	File getSceneXmlFile() throws IOException
	{
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			throw new IOException("Cannot mount media!");
		}

		File location = new File(getExternalFilesDir(null), "scene.xml");

		if (!location.exists())
		{

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED_READ_ONLY))
			{
				throw new IOException("Cannot mount media!");
			}
			// location.createNewFile();
			AssetManager am = getAssets();
			InputStream is = am.open("scenes.xml",
					AssetManager.ACCESS_STREAMING);
			OutputStream os = new FileOutputStream(location);

			byte[] buff = new byte[256];

			while (is.available() > 0)
			{
				buff = new byte[256];
				is.read(buff);
				os.write(buff);
			}
			is.close();
		}

		return location;
		
	}
}

class MyVerticalSeekBar extends VerticalSeekBar
{

	public MyVerticalSeekBar(Context context)
	{
		super(context);
		this.setPadding(50, 50, 50, 50);
	}
	
	public Scene scene;

}