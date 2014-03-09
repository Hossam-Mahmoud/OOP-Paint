package src;


import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import src.MyShape;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
 
public class ReadXMLFile {
public static void read(String path)
{
	 ArrayList<MyShape> eee = new  ArrayList<MyShape>();
	 
	  try {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new File(path));
		doc.getDocumentElement().normalize();
		ArrayList<String> list = new ArrayList<String>();
		list.add("MyPolygon");
		list.add("MyLine");
		list.add("MyTriangle");
		list.add("MySquare");
		list.add("MyCircle");
		list.add("MyRectangle");
		list.add("MyRectangularShape");
		list.add("MyEllipse");
//add all the classes pre-imported


		
		
		NodeList nList = doc.getElementsByTagName("Size");
		Node nNode=nList.item(0);
		Element element =(Element)nNode;
		int size = Integer.parseInt(element.getTextContent().replaceAll("\n", ""));
		
		
		
			nList = doc.getElementsByTagName("Imports");
		for(int temp=0;temp<nList.getLength();temp++){
			 nNode=nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				 
			      Element eElement = (Element) nNode;
String imp=getTagValue("Class", eElement);
if (imp.contains("Plugin")) {
	imp=imp.replace("Plugin", "");
	imp = imp.substring(0, imp.indexOf('.'));
}
boolean found = false;
for (int k = 0; k < engine.imports.size(); k++) {
	if (imp.equals(engine.imports.get(k))) {
		found = true;
	}
}
if (!found) {
	
	JOptionPane pane = new JOptionPane();
	pane.showMessageDialog(null, "Cannot load, Please import the extended shapes");
	return;

}
			    if(found){  
			  	list.add("Plugin"+imp+".jar");}
			   }
			
			
		}
		
engine.objects=new ArrayList<MyShape>();
engine.isDeleted=new ArrayList<Boolean>();
for(int i=0;i<size;i++){engine.objects.add(null);
engine.isDeleted.add(true);
}
		
int number =0;
		for(int z =0 ; z <list.size();z++){
nList=doc.getElementsByTagName(list.get(z));	 
	 
	for (int temp = 0; temp < nList.getLength(); temp++) {

		    nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

		      Element eElement = (Element) nNode;
		      number++;
		      
		     
		      MyShape e ;
		      String imp=eElement.getNodeName();
		      
		      if (imp.contains("Plugin")) {
		    	  imp=imp.replace("Plugin", "");
					imp = imp
							.substring(0, imp.indexOf('.'));
					Class c = Class.forName("src."+imp,true,Main.plug);
					e=(MyShape) c.newInstance();
					
					
				} else {
					 e= (MyShape) Class.forName("src."+imp)
						.newInstance();
					 }		      
		      
		      
		      
		      

e.getClass().getName();
e.setParameters2(getTagValue("Attributes", eElement).replaceAll("\n", ""));
		      
		   String cx [] =  getTagValue("Color", eElement).replaceAll("\n","").split(" ");
		   Color d = new Color(Integer.parseInt(cx[0]), Integer.parseInt(cx[2]), Integer.parseInt(cx[1]));
		   e.changeColor(d);
		   e.focused=false;
		     eee.add(e);
		      
		     int x = Integer.parseInt(getTagValue("order", eElement).replaceAll("\n", ""));
		     engine.objects.set(x, e);
		   engine.isDeleted.set(x, false);
		   }
		}
}
	  

	  } catch (Exception e) {
//		e.printStackTrace();
	  }
}
  private static String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
 
        Node nValue = (Node) nlList.item(0);
 
	return nValue.getNodeValue();
  }
 
}