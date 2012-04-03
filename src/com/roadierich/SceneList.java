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

public class SceneList
{
	public LinkedList<Scene> scenes;
	
	public void generateFromXML(File f)
	{
		DocumentBuilder db;
		try 
		{
			db = (DocumentBuilderFactory.newInstance()).newDocumentBuilder();
		
			Document doc = db.parse(f);
			
			Element curScene = (Element) doc.getElementsByTagName("scene").item(0);
			
			HashMap<Integer, Integer> channels = new HashMap<Integer, Integer>();
			
			while (curScene != null)
			{
				NodeList channelNodeList = curScene.getElementsByTagName("channel");
				Element curChannel = (Element) channelNodeList.item(0);
				while (curChannel != null)
				{
					channels.put(Integer.parseInt(curChannel.getAttribute("id")), Integer.parseInt(curChannel.getTextContent()));
					curChannel = (Element) curChannel.getNextSibling();
				}
				int time = -1;
				Element timeEle = (Element) curScene.getElementsByTagName("time").item(0);
				if (timeEle != null )
				{
					time = Integer.parseInt(timeEle.getTextContent());
				}
				scenes.add(new Scene(channels, time));
				
				curScene = (Element) curScene.getNextSibling();
			}
		} 
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}