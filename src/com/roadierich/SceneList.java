package com.roadierich;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class SceneList
{
	public LinkedList<Scene> scenes = new LinkedList<Scene>();

	public void generateFromXML(File f)
	{
		DocumentBuilder db;
		try
		{
			db = (DocumentBuilderFactory.newInstance()).newDocumentBuilder();

			Document doc = db.parse(f);

			NodeList sceneNodeList = doc.getElementsByTagName("scene");

			HashMap<Integer, Integer> channels = new HashMap<Integer, Integer>();

			for (int j = 0; j < sceneNodeList.getLength(); j++)
			{
				Element curScene = (Element) sceneNodeList.item(j);
				NodeList channelNodeList = curScene
						.getElementsByTagName("channel");
				// Element curChannel = (Element) channelNodeList.item(0);
				
				for (int i = 0; i < channelNodeList.getLength(); i++)
				{
					Element curChannel = (Element) channelNodeList.item(i);
					channels.put(
							Integer.parseInt(curChannel.getAttribute("id")),
							Integer.parseInt(curChannel.getTextContent()));
					// curChannel = (Element) (curChannel.getNextSibling());
				}
				int time = -1;
				Element timeEle = (Element) curScene.getElementsByTagName(
						"time").item(0);
				if (timeEle != null)
				{
					time = Integer.parseInt(timeEle.getTextContent());
				}
				scenes.add(new Scene(channels, time));

				//curScene = (Element) curScene.getNextSibling();
			}
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			Log.getStackTraceString(e);
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			Log.getStackTraceString(e);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			Log.getStackTraceString(e);
		}
//		
//		 
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), 5));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), 5));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), 1));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), 5));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), 5));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), 1));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), -1));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), -1));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), -1));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), -1));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), -1));
//		scenes.add(new Scene(new HashMap<Integer, Integer>(), -1));
	}

}