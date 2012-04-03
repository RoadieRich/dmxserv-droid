
package  com.roadierich;

import java.util.*;
//import com.roadierich.DMXServDroid2Activity;

import java.io.IOException;
import java.net.*;
//import android.net.*;

public class NetworkingThread
{
	final LinkedList<String> queue = new LinkedList<String>();
	
	public void run()
	{
		final NetworkingThread parent = this;
		thread = new Thread(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					if(sockAddr==null)
						parent.wait();
					parent.connect();
					
					while(true)
					{
						queue.wait();
						Iterator<String> it = queue.iterator();
						for (String s = it.next(); it.hasNext(); s = it.next())
						{
							parent.sendUDP(s);
						}
						Thread.sleep(50);
					}
					
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
	}
	
	@SuppressWarnings("unused")
	private Thread thread = null;
	
	private SocketAddress sockAddr = null;
	
	private DatagramSocket sock = null;
	
	public void setIP(InetAddress address)
	{
		sockAddr = new InetSocketAddress(address, 14000);
		this.notifyAll();
	}
	
	private void connect() throws SocketException
	{
		sock = new DatagramSocket();
		sock.connect(sockAddr);
	}
	
	private void sendUDP(String data) throws IOException {
		byte[] sendBuf = data.getBytes();
		DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, sockAddr);
		sock.send(packet);
	}
	
	public void queueData(String data)
	{
		queue.addLast(data);
		queue.notify();
	}
}