package com.roadierich;

import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SceneDelta {
	LinkedList<Delta> changes = new LinkedList<Delta>();
	public void addChange(int sceneId, int delta)
	{
		changes.push(new Delta(sceneId, delta, 0));
	}
	
	public void addChange(int sceneId, int delta, int timeInSecs)
	{
		changes.push(new Delta(sceneId, delta, timeInSecs));
	}
	
	public String toXML()
	{	
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			Document doc = db.newDocument();
			
			Element root = doc.createElement("scene-delta");
			doc.appendChild(root);
			for (Delta curChange = changes.pop(); !changes.isEmpty(); curChange = changes.pop())
			{
				Element sceneElem = doc.createElement("scene");
				sceneElem.setAttribute("id", curChange.scene.toString());
			
				Element levelElem = doc.createElement("level");
				levelElem.setTextContent(curChange.level.toString());
				
				sceneElem.appendChild(levelElem);
			
				if (curChange.time > 0)
				{
					Element timeElem = doc.createElement("time");
					timeElem.setTextContent(curChange.time.toString());
					
					sceneElem.appendChild(timeElem);
				}
					
					
				
				
				root.appendChild(sceneElem);
			}
			
		
	
			return doc.toString();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
		
	}
	
}

class Delta
{
	public Integer level;
	public Integer time;
	public Integer scene;

	public Delta(int sceneId, int level, int timeInSecs)
	{
		this.scene = sceneId;
		this.level = level;
		this.time = timeInSecs;
	}
}