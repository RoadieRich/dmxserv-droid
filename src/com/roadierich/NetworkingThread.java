package com.roadierich;

import java.util.*;
//import com.roadierich.DMXServDroid2Activity;

import java.io.IOException;
import java.net.*;

import android.app.*;
import android.widget.*;
import android.content.*;
import android.content.DialogInterface.OnClickListener;
//import android.net.*;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

public class NetworkingThread
{
	final LinkedList<String> queue = new LinkedList<String>();

	final DMXServDroid2Activity context;

	public NetworkingThread(DMXServDroid2Activity c)
	{
		this.context = c;
	}

	public void run()
	{

		final NetworkingThread parent = this;
		thread = new Thread(new Runnable()
		{
			@SuppressWarnings("unused")
			public void run()
			{
				while (context.ipAddress.contentEquals(""))
				{
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
				try
				{

					setIP(InetAddress.getByName(context.ipAddress));

					parent.connect();
					if (false)
					{
						byte[] sendBuf = "Hello world!".getBytes();
						DatagramPacket packet = new DatagramPacket(sendBuf,
								sendBuf.length);
						udpSock.send(packet);
					}
					// parent.sendUDP("Hello world!");
					else
					{
						while (true)
						{
							synchronized (queue)
							{
								if (queue.size() > 0)
								{
									String s;
									while (!queue.isEmpty())
									{

										s = queue.pop();
										// parent.sendUDP(s);

										byte[] sendBuf = s.getBytes();
										DatagramPacket packet = new DatagramPacket(
												sendBuf, sendBuf.length);
										udpSock.send(packet);
									}
								}
							}
							Thread.sleep(50);
						}
					}

				}
				catch (Exception e)
				{
					Log.getStackTraceString(e);
					//
					// AlertDialog.Builder builder = new AlertDialog.Builder(
					// context);
					// builder.setMessage("Cannot connect to server!")
					// .setTitle("Error!")
					// .setNeutralButton("OK", new OnClickListener()
					// {
					//
					// public void onClick(DialogInterface dialog, int which)
					// {
					// // TODO Auto-generated method stub
					//
					// }
					// }).show();
				}
			}
		});
		thread.start();
	}

	private Thread thread = null;

	private SocketAddress sockAddr = null;

	private DatagramSocket udpSock = null;

	public void setIP(InetAddress address)
	{
		sockAddr = new InetSocketAddress(address, 14000);
	}

	private void connect() throws SocketException
	{
		udpSock = new DatagramSocket();
		udpSock.connect(sockAddr);
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void sendUDP(String data) throws IOException
	{

	}

	public void queueData(String data)
	{
		queue.addLast(data);
	}
}