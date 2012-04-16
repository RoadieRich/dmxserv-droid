package com.roadierich;

import java.io.StringWriter;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.util.Log;

public class SceneDelta
{
	LinkedList<Delta> changes = new LinkedList<Delta>();

	public void addChange(Scene scene, int delta)
	{
		changes.push(new Delta(scene, delta, 0));
	}

	public void addChange(Scene scene, int delta, int timeInSecs)
	{
		changes.push(new Delta(scene, delta, timeInSecs));
	}

	public String toXML()
	{
		try
		{
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			Document doc = db.newDocument();

			Element root = doc.createElement("scene-delta");
			doc.appendChild(root);
			Delta curChange;
			while (!changes.isEmpty())
			{
				curChange = changes.pop();
				
				Element sceneElem = doc.createElement("scene");

				sceneElem.setAttribute("id",
						Integer.toString(curChange.scene.id));
				sceneElem.setAttribute("level", curChange.level.toString());
				if (curChange.time >= 0)
					sceneElem.setAttribute("time", curChange.time.toString());
			
				root.appendChild(sceneElem);
			}
			//Convert Document to a string
			//Doc.toString() doesn't do what you expect it to be
			
			try
			{
				DOMSource domSource = new DOMSource(doc);
				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.transform(domSource, result);
				
				String ret = null;
				try
				{
					ret = writer.toString();
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ret;
			}
			catch (TransformerException exp)
			{
				Log.getStackTraceString(exp);
				return null;
			}
			
			//return doc.toString();
			

		}

		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			Log.getStackTraceString(e);
			return null;
		}
	}
}

class Delta
{
	public Integer level;
	public Integer time;
	public Scene scene;

	public Delta(Scene sceneId, int level, int timeInSecs)
	{
		this.scene = sceneId;
		this.level = level;
		this.time = timeInSecs;
	}
}